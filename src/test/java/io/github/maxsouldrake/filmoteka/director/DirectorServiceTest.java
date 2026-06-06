package io.github.maxsouldrake.filmoteka.director;

import io.github.maxsouldrake.filmoteka.director.dto.DetailedDirectorResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static io.github.maxsouldrake.filmoteka.testdata.DirectorTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void shouldFindDirectorByIdIfExists() {
        Director loadedDirector = loadedDirector();

        when(directorRepository.findById(DIRECTOR_ID)).thenReturn(Optional.of(loadedDirector));
        when(directorMapper.directorToDetailedDirectorResponse(loadedDirector)).thenReturn(detailedDirectorResponse());

        DetailedDirectorResponse response = directorService.findById(DIRECTOR_ID);

        assertThat(response).isEqualTo(detailedDirectorResponse());
        verify(directorRepository).findById(DIRECTOR_ID);
        verify(directorMapper).directorToDetailedDirectorResponse(loadedDirector);
    }

    @Test
    void shouldThrowIfDoesNotExist() {
        when(directorRepository.findById(DIRECTOR_ID)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> directorService.findById(DIRECTOR_ID));

        verify(directorRepository).findById(DIRECTOR_ID);
        verifyNoInteractions(directorMapper);
    }
}