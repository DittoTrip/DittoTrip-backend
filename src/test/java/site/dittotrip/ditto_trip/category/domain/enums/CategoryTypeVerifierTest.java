package site.dittotrip.ditto_trip.category.domain.enums;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryTypeVerifierTest {

    @Test
    void verifyType_true() {
        assertThat(CategoryTypeVerifier.verifyType(CategoryMajorType.PERSON, CategorySubType.PERSON_ACTOR)).isEqualTo(true);
        assertThat(CategoryTypeVerifier.verifyType(CategoryMajorType.CONTENT, CategorySubType.CONTENT_DRAMA)).isEqualTo(true);
    }

    @Test
    void verifyType_false() {
        assertThat(CategoryTypeVerifier.verifyType(CategoryMajorType.PERSON, CategorySubType.CONTENT_ENTERTAINMENT)).isEqualTo(false);
        assertThat(CategoryTypeVerifier.verifyType(CategoryMajorType.CONTENT, CategorySubType.PERSON_ACTOR)).isEqualTo(false);
    }


}