package site.dittotrip.ditto_trip.ditto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.ditto.domain.DittoBookmark;
import site.dittotrip.ditto_trip.ditto.domain.dto.*;
import site.dittotrip.ditto_trip.ditto.repository.DittoBookmarkRepository;
import site.dittotrip.ditto_trip.ditto.repository.DittoRepository;
import site.dittotrip.ditto_trip.review.exception.NoAuthorityException;
import site.dittotrip.ditto_trip.review.exception.TooManyImagesException;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DittoService {

    private final DittoRepository dittoRepository;
    private final DittoBookmarkRepository dittoBookmarkRepository;

    public DittoListRes findDittoList(User user, Pageable pageable) {
        Page<Ditto> page = dittoRepository.findAll(pageable);
        return DittoListRes.fromEntities(page);
    }

    public DittoListRes findDittoListInBookmark(User user) {
        List<DittoBookmark> dittoBookmarks = dittoBookmarkRepository.findByUser(user);

        List<Ditto> dittos = new ArrayList<>();
        for (DittoBookmark dittoBookmark : dittoBookmarks) {
            dittos.add(dittoBookmark.getDitto());
        }

        return DittoListRes.fromEntities(dittos);
    }

    public DittoDetailRes findDittoDetail(Long dittoId, User user) {
        Ditto ditto = dittoRepository.findByIdWithUser(dittoId).orElseThrow(NoSuchElementException::new);

        Boolean isMine = getIsMine(ditto, user);
        Long myBookmarkId = getMyBookmarkId(ditto, user);

        return DittoDetailRes.fromEntity(ditto, isMine, myBookmarkId);
    }

    @Transactional(readOnly = false)
    public void saveDitto(User user, DittoSaveReq saveReq,
                           List<MultipartFile> multipartFiles) {
        // 최대 이미지 개수 수정할 수 있음
        if (multipartFiles.size() > 20) {
            throw new TooManyImagesException();
        }

        Ditto ditto = saveReq.toEntity(user);

        // image 처리

        dittoRepository.save(ditto);
    }

    @Transactional(readOnly = false)
    public void modifyDitto(Long dittoId, User user, DittoModifyReq modifyReq,
                            List<MultipartFile> multipartFiles) {
        Ditto ditto = dittoRepository.findByIdWithUser(dittoId).orElseThrow(NoSuchElementException::new);

        if(!ditto.getUser().equals(user)) {
            throw new NoAuthorityException();
        }

        // 최대 이미지 개수 수정할 수 있음
        if (ditto.getDittoImages().size() + multipartFiles.size() - modifyReq.getRemovedImageIds().size() > 20) {
            throw new NoAuthorityException();
        }

        modifyReq.modifyEntity(ditto);

        // image 처리
    }

    @Transactional(readOnly = false)
    public void removeDitto(Long dittoId, User user) {
        Ditto ditto = dittoRepository.findById(dittoId).orElseThrow(NoSuchElementException::new);

        if(!ditto.getUser().equals(user)) {
            throw new NoAuthorityException();
        }

        // image 처리

        dittoRepository.delete(ditto);
    }

    private Boolean getIsMine(Ditto ditto, User user) {
        if (user == null) {
            return Boolean.FALSE;
        }

        if (ditto.getUser().getId() == user.getId()) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    private Long getMyBookmarkId(Ditto ditto, User user) {
        if (user == null) {
            return null;
        }

        Optional<DittoBookmark> dittoBookmark = dittoBookmarkRepository.findByDittoAndUser(ditto, user);
        return dittoBookmark.map(DittoBookmark::getId).orElse(null);
    }

}
