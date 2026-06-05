package io.github.maxsouldrake.filmoteka.director;

import io.github.maxsouldrake.filmoteka.director.dto.DirectorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DirectorService {

    private final DirectorRepository directorRepository;
    private final DirectorMapper directorMapper;

    public List<Director> findAll() {
        return directorRepository.findAll();
    }

    public Optional<Director> findById(Long id) {
        return directorRepository.findById(id);
    }

    @Transactional
    public Director findOrCreate(DirectorRequest request) {
        return directorRepository.findByName(request.name()).orElseGet(
                () -> directorRepository.save(directorMapper.directorRequestToDirector(request))
        );
    }

    @Transactional
    public void deleteById(Long id) {
        directorRepository.deleteById(id);
    }
}
