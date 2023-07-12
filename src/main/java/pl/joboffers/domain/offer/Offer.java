package pl.joboffers.domain.offer;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@Document(collection = "offers")
record Offer(@Id String id,
             @Field("url") @Indexed(unique = true) String offerUrl,
             @Field("position") String jobPosition,
             @Field("company") String companyName,
             @Field("salary")String earnings) {
}
