package io.github.maxsouldrake.filmoteka.director;

import io.github.maxsouldrake.filmoteka.director.dto.DetailedDirectorResponse;
import io.github.maxsouldrake.filmoteka.director.dto.DirectorRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.NoSuchElementException;
import java.util.Optional;

import static io.github.maxsouldrake.filmoteka.director.DirectorTestData.*;
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

    @Test
    void shouldUpdateDirectorIfExists() {
        Director loadedDirector = loadedDirector();
        loadedDirector.setName("old name");

        when(directorRepository.findById(DIRECTOR_ID)).thenReturn(Optional.of(loadedDirector));

        doAnswer(updateNameOnly()).when(directorMapper).updateDirectorRequestToDirector(any(), any());

        when(directorRepository.save(any(Director.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(directorMapper.directorToDetailedDirectorResponse(any(Director.class))).thenReturn(detailedDirectorResponse());

        DetailedDirectorResponse response = directorService.updateDirector(DIRECTOR_ID, directorRequest());

        assertThat(response).isEqualTo(detailedDirectorResponse());
        ArgumentCaptor<Director> captor = ArgumentCaptor.forClass(Director.class);

        verify(directorRepository).save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo(DIRECTOR_NAME);
        verify(directorMapper).updateDirectorRequestToDirector(directorRequest(), loadedDirector);
        verify(directorMapper).directorToDetailedDirectorResponse(any(Director.class));
    }

    private static Answer<Void> updateNameOnly() {
        return invocation -> {
            DirectorRequest request = invocation.getArgument(0);
            Director director = invocation.getArgument(1);
            director.setName(request.name());
            return null;
        };
    }
}