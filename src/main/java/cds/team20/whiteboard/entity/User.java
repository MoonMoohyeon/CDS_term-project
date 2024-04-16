package cds.team20.whiteboard.entity;

import falcons.omok.constant.security.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Builder
@Entity
@AllArgsConstructor
@Table(name = "Users")
public class User extends Time{
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

    public User() {
    }

    public User updateModifiedDate() {
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
