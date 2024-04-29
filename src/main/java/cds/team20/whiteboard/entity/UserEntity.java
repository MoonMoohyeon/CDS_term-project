package cds.team20.whiteboard.entity;

//import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserEntity extends Time{
    private Long Index;
    private String userId;

    public UserEntity() {
    }

    public UserEntity updateModifiedDate() {
        this.onPreUpdate();
        return this;
    }

    public void setIndex(Long index) {
        Index = index;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
