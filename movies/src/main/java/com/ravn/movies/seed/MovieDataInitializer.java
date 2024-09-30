package com.ravn.movies.seed;

import com.ravn.movies.domain.Category;
import com.ravn.movies.domain.Movie;
import com.ravn.movies.domain.User;
import com.ravn.movies.dto.enums.Role;
import com.ravn.movies.repository.ICategoryRepository;
import com.ravn.movies.repository.IMovieRepository;
import com.ravn.movies.repository.IUserRepository;
import com.ravn.movies.utils.Utils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieDataInitializer implements CommandLineRunner {
    private final IMovieRepository movieRepository;
    private final ICategoryRepository categoryRepository;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public MovieDataInitializer(IMovieRepository movieRepository, ICategoryRepository categoryRepository, IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.movieRepository = movieRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (movieRepository.count() == 0 && userRepository.count() == 0) {
            User admin = new User();
            admin.setEmail("admin@example.com");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN.toString());
            admin = userRepository.save(admin);

            User user = new User();
            user.setEmail("user@example.com");
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRole(Role.USER.toString());
            user = userRepository.save(user);

            Category dramaCategory = new Category();
            dramaCategory.setName("Drama");
            categoryRepository.save(dramaCategory);

            Category comedyCategory = new Category();
            comedyCategory.setName("Comedy");
            categoryRepository.save(comedyCategory);

            movieRepository.saveAll(List.of(
                Movie.builder().name("The Shawshank Redemption").synopsis("A story of hope and friendship inside a prison.").year(1994L).category(dramaCategory).userId(admin.getId()).imagePoster("https://housefy-dev-bucket.s3.amazonaws.com/ravn/1727721832517-redemption") .createdDate(Utils.getCurrentTimeSeconds()).build(),
                Movie.builder().name("The Godfather").synopsis("The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.").year(1972L).category(dramaCategory).userId(admin.getId()).imagePoster("https://housefy-dev-bucket.s3.amazonaws.com/ravn/1727721881240-el-padrino").createdDate(Utils.getCurrentTimeSeconds()).build(),
                Movie.builder().name("The Dark Knight").synopsis("When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.").year(2008L).category(dramaCategory).userId(admin.getId()).imagePoster("https://housefy-dev-bucket.s3.amazonaws.com/ravn/1727721919988-darknight").createdDate(Utils.getCurrentTimeSeconds()).build(),
                Movie.builder().name("The Mask").synopsis("When timid bank clerk Stanley Ipkiss (Jim Carrey) discovers a magical mask containing the spirit of the Norse god Loki, his entire life changes.").year(1994L).category(dramaCategory).imagePoster("https://housefy-dev-bucket.s3.amazonaws.com/ravn/1727721718119-the-mask").userId(admin.getId()).createdDate(Utils.getCurrentTimeSeconds()).build()
            ));
        }
    }
}
