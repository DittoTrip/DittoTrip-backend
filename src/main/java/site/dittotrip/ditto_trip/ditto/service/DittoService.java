package site.dittotrip.ditto_trip.ditto.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.alarm.domain.Alarm;
import site.dittotrip.ditto_trip.alarm.repository.AlarmRepository;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.ditto.domain.DittoBookmark;
import site.dittotrip.ditto_trip.ditto.domain.DittoComment;
import site.dittotrip.ditto_trip.ditto.domain.dto.*;
import site.dittotrip.ditto_trip.ditto.repository.DittoBookmarkRepository;
import site.dittotrip.ditto_trip.ditto.repository.DittoCommentRepository;
import site.dittotrip.ditto_trip.ditto.repository.DittoRepository;
import site.dittotrip.ditto_trip.follow.domain.Follow;
import site.dittotrip.ditto_trip.hashtag.domain.Hashtag;
import site.dittotrip.ditto_trip.hashtag.domain.HashtagDitto;
import site.dittotrip.ditto_trip.hashtag.repository.HashtagRepository;
import site.dittotrip.ditto_trip.review.exception.NoAuthorityException;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;
import site.dittotrip.ditto_trip.utils.S3Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DittoService {

    private final DittoRepository dittoRepository;
    private final DittoBookmarkRepository dittoBookmarkRepository;
    private final DittoCommentRepository dittoCommentRepository;
    private final HashtagRepository hashtagRepository;
    private final AlarmRepository alarmRepository;
    private final S3Service s3Service;
    private final UserRepository userRepository;

    public DittoListRes findDittoList(User reqUser, Pageable pageable) {
        Page<Ditto> page = dittoRepository.findAll(pageable);
        return DittoListRes.fromEntities(page);
    }

    public DittoListRes findDittoListInBookmark(User reqUser) {
        List<DittoBookmark> dittoBookmarks = dittoBookmarkRepository.findByUser(reqUser);

        List<Ditto> dittos = new ArrayList<>();
        for (DittoBookmark dittoBookmark : dittoBookmarks) {
            dittos.add(dittoBookmark.getDitto());
        }

        return DittoListRes.fromEntities(dittos);
    }

    public DittoListRes findDittoListBySearch(User reqUser, String word, Pageable pageable) {
        Page<Ditto> page = dittoRepository.findByTitleContaining(word, pageable);
        return DittoListRes.fromEntities(page);
    }

    public DittoDetailRes findDittoDetail(Long dittoId, User reqUser) {
        Ditto ditto = dittoRepository.findByIdWithUser(dittoId).orElseThrow(NoSuchElementException::new);

        List<DittoComment> dittoComments = dittoCommentRepository.findParentCommentByDitto(ditto);
        Long dittoCount = dittoCommentRepository.countByDitto(ditto);

        Boolean isMine = getIsMine(ditto, reqUser);
        Long myBookmarkId = getMyBookmarkId(ditto, reqUser);

        return DittoDetailRes.fromEntity(ditto, dittoComments, dittoCount.intValue(), isMine, myBookmarkId, reqUser);
    }

    @Transactional(readOnly = false)
    public void saveDitto(User reqUser, DittoSaveReq saveReq,
                           MultipartFile multipartFile) {
        User user = userRepository.findById(reqUser.getId()).get();

        Ditto ditto = saveReq.toEntity(user);
        dittoRepository.save(ditto);

        // hashtag 처리
        for (String name : saveReq.getHashtagNames()) {
            Hashtag hashtag = hashtagRepository.findByName(name).orElse(null);
            if (hashtag == null) {
                hashtag = new Hashtag(name);
                hashtagRepository.save(hashtag);
            }
            ditto.getHashtagDittos().add(new HashtagDitto(hashtag, ditto));
        }

        // image 처리
        String imagePath = s3Service.uploadFile(multipartFile);
        ditto.setImagePath(imagePath);

        // 알림 처리
        processAlarmInSaveDitto(ditto);
    }

    @Transactional(readOnly = false)
    public void modifyDitto(Long dittoId, User reqUser, DittoSaveReq saveReq,
                            MultipartFile multipartFile) {
        Ditto ditto = dittoRepository.findByIdWithUser(dittoId).orElseThrow(NoSuchElementException::new);

        if (ditto.getUser().getId() != reqUser.getId()) {
            throw new NoAuthorityException();
        }

        saveReq.modifyEntity(ditto);

        // image 처리
        s3Service.deleteFile(ditto.getImagePath());
        String imagePath = s3Service.uploadFile(multipartFile);
        ditto.setImagePath(imagePath);

        // hashtag 처리
        ditto.getHashtagDittos().clear();
        for (String name : saveReq.getHashtagNames()) {
            Hashtag hashtag = hashtagRepository.findByName(name).orElse(null);
            if (hashtag == null) {
                hashtag = new Hashtag(name);
                hashtagRepository.save(hashtag);
            }
            ditto.getHashtagDittos().add(new HashtagDitto(hashtag, ditto));
        }
    }

    @Transactional(readOnly = false)
    public void removeDitto(Long dittoId, User reqUser) {
        Ditto ditto = dittoRepository.findById(dittoId).orElseThrow(NoSuchElementException::new);

        if (ditto.getUser().getId() != reqUser.getId()) {
            throw new NoAuthorityException();
        }

        s3Service.deleteFile(ditto.getImagePath());
        dittoRepository.delete(ditto);
    }

    private Boolean getIsMine(Ditto ditto, User reqUser) {
        if (reqUser == null) {
            return Boolean.FALSE;
        }

        if (ditto.getUser().getId() == reqUser.getId()) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    private Long getMyBookmarkId(Ditto ditto, User reqUser) {
        if (reqUser == null) {
            return null;
        }

        Optional<DittoBookmark> dittoBookmark = dittoBookmarkRepository.findByDittoAndUser(ditto, reqUser);
        return dittoBookmark.map(DittoBookmark::getId).orElse(null);
    }

    private void processAlarmInSaveDitto(Ditto ditto) {
        String title = "새로운 디토가 있어요 !!";
        String body = ditto.getUser() + " 님이 디토를 작성했어요 !!";
        String path = "/ditto/" + ditto.getId();
        List<User> targets = new ArrayList<>();
        List<Follow> followeds = ditto.getUser().getFolloweds();
        for (Follow followed : followeds) {
            targets.add(followed.getFollowingUser());
        }

        alarmRepository.saveAll(Alarm.createAlarms(title, body, path, targets));
    }

}
