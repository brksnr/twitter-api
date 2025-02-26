package com.example.demo;

import com.example.demo.dto.tweetDtos.TweetDto;
import com.example.demo.dto.tweetDtos.TweetUserDto;
import com.example.demo.entity.Tweets;
import com.example.demo.entity.user.User;
import com.example.demo.exceptions.ApiException;
import com.example.demo.repository.TweetRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TweetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TweetServiceTest {

    @Mock
    private TweetRepository tweetRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TweetService tweetService;

    private User user;
    private Tweets tweet;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setUserName("testuser");

        tweet = new Tweets();
        tweet.setId(1L);
        tweet.setContent("Test Tweet");
        tweet.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        tweet.setUser(user);
    }

    @Test
    void createTweet_ShouldReturnSavedTweet() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(tweetRepository.save(any(Tweets.class))).thenReturn(tweet);

        Tweets result = tweetService.createTweet("Test Tweet", 1L);

        assertNotNull(result);
        assertEquals("Test Tweet", result.getContent());
        verify(tweetRepository, times(1)).save(any(Tweets.class));
    }

    @Test
    void createTweet_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> {
            tweetService.createTweet("Test Tweet", 1L);
        });

        assertEquals("Kullanıcı bulunamadı!", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void getAllTweets_ShouldReturnTweetDtoList() {
        when(tweetRepository.findAll()).thenReturn(Arrays.asList(tweet));

        List<TweetDto> result = tweetService.getAllTweets();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Test Tweet", result.get(0).getContent());
    }

    @Test
    void getAllTweetsById_ShouldReturnUserTweets() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(user.getTweets()).thenReturn(List.of(new Tweets()));

        List<Tweets> result = tweetService.getAllTweetsById(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Tweet", result.get(0).getContent());
    }

    @Test
    void getAllTweetsById_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> {
            tweetService.getAllTweetsById(1L);
        });

        assertEquals("Kullanıcı bulunamadı!", exception.getMessage());
    }

    @Test
    void getTweetWithInfo_ShouldReturnTweet() {
        when(tweetRepository.findById(1L)).thenReturn(Optional.of(tweet));

        Tweets result = tweetService.getTweetWithInfo(1L);

        assertNotNull(result);
        assertEquals("Test Tweet", result.getContent());
    }

    @Test
    void getTweetWithInfo_ShouldThrowException_WhenTweetNotFound() {
        when(tweetRepository.findById(1L)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> {
            tweetService.getTweetWithInfo(1L);
        });

        assertEquals("İlgili tweet bulunamadı.", exception.getMessage());
    }

    @Test
    void deleteTweetById_ShouldDeleteTweet_WhenUserOwnsTweet() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(tweetRepository.findById(1L)).thenReturn(Optional.of(tweet));

        tweetService.deleteTweetById(1L, 1L);

        verify(tweetRepository, times(1)).delete(tweet);
    }

    @Test
    void deleteTweetById_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> {
            tweetService.deleteTweetById(1L, 1L);
        });

        assertEquals("İlgili kullanıcı bulunamadı.", exception.getMessage());
    }

    @Test
    void deleteTweetById_ShouldThrowException_WhenTweetNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(tweetRepository.findById(1L)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> {
            tweetService.deleteTweetById(1L, 1L);
        });

        assertEquals("ilgili tweet bulunamadı.", exception.getMessage());
    }

    @Test
    void deleteTweetById_ShouldThrowException_WhenUserNotOwner() {
        User anotherUser = new User();
        anotherUser.setId(2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(tweetRepository.findById(1L)).thenReturn(Optional.of(tweet));

        tweet.setUser(anotherUser);

        ApiException exception = assertThrows(ApiException.class, () -> {
            tweetService.deleteTweetById(1L, 1L);
        });

        assertEquals("Bu tweeti silme yetkiniz yok!", exception.getMessage());
    }

    @Test
    void updateTweet_ShouldUpdateAndReturnTweet() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(tweetRepository.findById(1L)).thenReturn(Optional.of(tweet));
        when(tweetRepository.save(any(Tweets.class))).thenReturn(tweet);

        Tweets result = tweetService.updateTweet(1L, 1L, "Updated Tweet");

        assertNotNull(result);
        assertEquals("Updated Tweet", result.getContent());
    }

    @Test
    void updateTweet_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> {
            tweetService.updateTweet(1L, 1L, "Updated Tweet");
        });

        assertEquals("İlgili kullanıcı bulunamadı.", exception.getMessage());
    }

    @Test
    void updateTweet_ShouldThrowException_WhenTweetNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(tweetRepository.findById(1L)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> {
            tweetService.updateTweet(1L, 1L, "Updated Tweet");
        });

        assertEquals("ilgili tweet bulunamadı.", exception.getMessage());
    }

    @Test
    void updateTweet_ShouldThrowException_WhenUserNotOwner() {
        User anotherUser = new User();
        anotherUser.setId(2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(tweetRepository.findById(1L)).thenReturn(Optional.of(tweet));

        tweet.setUser(anotherUser);

        ApiException exception = assertThrows(ApiException.class, () -> {
            tweetService.updateTweet(1L, 1L, "Updated Tweet");
        });

        assertEquals("Bu tweeti değiştirme yetkiniz yok!", exception.getMessage());
    }
}
