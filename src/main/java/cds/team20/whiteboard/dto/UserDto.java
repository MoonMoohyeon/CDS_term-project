package cds.team20.whiteboard.dto;

import cds.team20.whiteboard.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO는 Data Transfer Object의 약자입니다. 컨트롤러 - 서비스 - 엔티티 계층 간 데이터 교환을 위한 클래스입니다.
 * 엔티티는 DB와 매핑되는 클래스입니다.
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data  // getter, setter포함함
public class UserDto {
    private Long userIndex;
    private String userId;
    private String userEncryptPwd;

    public UserEntity toEntity() {
        return UserEntity.builder()
                .userId(userId)
                .build();
    }
}
