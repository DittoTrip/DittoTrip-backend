package site.dittotrip.ditto_trip.category.domain.enums;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CategoryTypeVerifier {

    public static Boolean verifyType(CategoryMajorType majorType, CategorySubType subType) {
        String subTypeFirst = subType.toString().split("_")[0];
        return majorType.toString().equals(subTypeFirst);
    }

}