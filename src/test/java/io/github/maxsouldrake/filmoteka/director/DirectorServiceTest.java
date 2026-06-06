package io.github.maxsouldrake.filmoteka.director;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static io.github.maxsouldrake.filmoteka.testdata.DirectorTestData.*;
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
        Director loadedDirector = loadedDirector();
        when(directorRepository.findByName(DIRECTOR_NAME)).thenReturn(Optional.of(loadedDirector));

        Director director = directorService.findOrCreate(directorRequest());
        assertThat(director).isEqualTo(loadedDirector);
        verify(directorRepository, never()).save(any());
    }

    @Test
    void shouldCreateDirectorIfNotFound() {
        Director director = director();
        when(directorRepository.findByName(DIRECTOR_NAME)).thenReturn(Optional.empty());
        when(directorMapper.directorRequestToDirector(directorRequest())).thenReturn(director);
        when(directorRepository.save(director)).thenReturn(loadedDirector());

        Director loadedDirector = directorService.findOrCreate(directorRequest());
        assertThat(loadedDirector.getName()).isEqualTo(DIRECTOR_NAME);
        verify(directorRepository).save(director);
    }

}