package model;

import lombok.Data;

@Data
public class UserModelForCreateRequest {
    String name, job, id, createdAt;
}
