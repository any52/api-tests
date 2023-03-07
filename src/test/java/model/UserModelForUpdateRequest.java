package model;

import lombok.Data;

@Data
public class UserModelForUpdateRequest {
    String name, job, updatedAt;
}
