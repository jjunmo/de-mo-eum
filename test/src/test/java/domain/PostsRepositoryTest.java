package test;

import domain.Posts;
import domain.PostsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class PostsRepositoryTest {


    @Autowired
    private PostsRepository postsRepository;


    @Test
    @DisplayName("3.JPA 테스트")
    public void inoutTest(){
        String title ="테스트 제목";
        String contents ="테스트 내용";

        postsRepository.save(Posts.builder()
                .title(title)
                .contents(contents)
                .build());

        List<Posts> postsList=postsRepository.findAll();

        Posts posts=postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContents()).isEqualTo(contents);

    }


}
