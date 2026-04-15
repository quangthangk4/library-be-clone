package com.library.recommendation.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.library.user.domain.enums.FacultyEnum;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublicationRatingResponse {

  @JsonSerialize(using = ToStringSerializer.class)
  private Long ratingId;
  private int star;
  private String comment;
  private int helpfulCount;

  private String fullName;
  private String profilePictureUrl;
  private String studentId;
  private FacultyEnum faculty;
  private Instant createdAt;

  public String getFaculty() {
    return faculty != null ? faculty.getName() : null;
  }
}
