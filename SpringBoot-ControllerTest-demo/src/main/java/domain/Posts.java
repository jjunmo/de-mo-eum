package domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Getter
@NoArgsConstructor
@Entity
public class Posts{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;

    private String contents;

    @Builder
    public Posts(Integer id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }
}
