package site.dittotrip.ditto_trip.ditto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.ditto.domain.DittoBookmark;
import site.dittotrip.ditto_trip.ditto.domain.dto.DittoDetailRes;
import site.dittotrip.ditto_trip.ditto.domain.dto.DittoModifyReq;
import site.dittotrip.ditto_trip.ditto.domain.dto.DittoSaveReq;
import site.dittotrip.ditto_trip.ditto.repository.DittoBookmarkRepository;
import site.dittotrip.ditto_trip.ditto.repository.DittoRepository;
import site.dittotrip.ditto_trip.review.exception.NoAuthorityException;
import site.dittotrip.ditto_trip.review.exception.TooManyImagesException;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DittoService {

    private final DittoRepository dittoRepository;
    private final DittoBookmarkRepository dittoBookmarkRepository;

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

        if (ditto.getUser().equals(user)) {
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
