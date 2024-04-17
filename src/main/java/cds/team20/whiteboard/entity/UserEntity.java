package cds.team20.whiteboard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Entity
@AllArgsConstructor
@Table(name = "Users")
public class UserEntity extends Time{
    @Index
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_index", nullable = false)
    private Long Index;

    /*사용자 이름입니다.*/
    @Column(name = "user_id", nullable = false)
    private String userId;

//    @Column(name = "user_encrypt_pwd", length = 40)
//    private String userEncryptPwd;


//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")

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
