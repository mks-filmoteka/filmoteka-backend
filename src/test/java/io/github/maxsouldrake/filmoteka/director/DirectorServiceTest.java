package io.github.maxsouldrake.filmoteka.director;

import io.github.maxsouldrake.filmoteka.director.dto.DirectorRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DirectorServiceTest {

    @Mock
    private DirectorRepository directorRepository;

    @Mock
    DirectorMapper directorMapper;

    @InjectMocks
    private DirectorService directorService;

    @Test
    void shouldNotCreateDirectorIfFound() {
        DirectorRequest request = new DirectorRequest("test name");
        Director existingDirector = Director.builder().id(1L).name("test name").build();
        when(directorRepository.findByName("test name")).thenReturn(Optional.of(existingDirector));
        Director director = directorService.findOrCreate(request);
        assertThat(director).isEqualTo(existingDirector);
        verify(directorRepository, never()).save(any());
    }

    @Test
    void shouldCreateDirectorIfNotFound() {
        DirectorRequest request = new DirectorRequest("test name");
        when(directorRepository.findByName("test name")).thenReturn(Optional.empty());
        Director mappedDirector = Director.builder().name("test name").build();
        when(directorMapper.directorRequestToDirector(request)).thenReturn(mappedDirector);
        when(directorRepository.save(mappedDirector)).thenReturn(mappedDirector);
        Director director = directorService.findOrCreate(request);
        assertThat(director.getName()).isEqualTo("test name");
        verify(directorRepository).save(mappedDirector);
    }

}