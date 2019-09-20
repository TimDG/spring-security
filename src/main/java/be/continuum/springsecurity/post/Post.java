package be.continuum.springsecurity.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.UUID;

import static java.time.ZonedDateTime.now;

@Entity
@Table(indexes = @Index(name="externalId", columnList = "externalId", unique = true))
@Data
public class Post {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String author;
    private String title;
    private String content;

    @Column(columnDefinition = "TIMESTAMP not null default current_timestamp")
    @Setter(AccessLevel.NONE)
    private final ZonedDateTime created;

    @Setter(AccessLevel.NONE)
    private ZonedDateTime lastEdited;

    @Type(type="yes_no")
    @Column(columnDefinition = "varchar(1) not null default 'N'")
    private boolean deleted = false;

    @Column(columnDefinition = "varchar(36) not null")
    @Setter(AccessLevel.NONE)
    private final String externalId;

    public Post() {
        externalId = UUID.randomUUID().toString();
        created = now();
    }

    public String getFormattedTime() {
        return created.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG));
    }

    void update(Post post) {
        this.content = post.content;
        this.title = post.title;
        this.author = post.author;
        this.deleted = post.deleted;
        this.lastEdited = now();
    }
}
