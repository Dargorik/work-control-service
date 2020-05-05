package com.workhelper.solution.work.control.controller;


import com.workhelper.solution.work.control.entity.Hero;
import com.workhelper.solution.work.control.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
@RestController
public class UserController {
    private final static List<Hero> heroes = new ArrayList<>();
    private final Map<UUID, Hero> heroesById;
    {
        heroes.add(new Hero("Egor", UUID.randomUUID()));
        heroes.add(new Hero("Andrey", UUID.randomUUID()));
        heroes.add(new Hero("Pavel", UUID.randomUUID()));
        heroes.add(new Hero("Olga", UUID.randomUUID()));
        heroes.add(new Hero("Nikita", UUID.randomUUID()));
        heroesById = heroes.stream()
                .collect(Collectors.toMap(Hero::getId, Function.identity()));
    }


    @GetMapping("/getUser")
    public User getUser() {
        User user = User.builder()
                .firstName("Egor")
                .lastName(("GGGG"))
                .build();
        return user;
    }

    @GetMapping("/api/heroes")
    public List<Hero> getHeroes() {
        return heroes;
    }

    @GetMapping("/api/heroes/{id}")
    public Hero getHero(@PathVariable Integer id) {
        return heroesById.get(id);
    }

    @PutMapping("/api/heroes")
    public Hero updateHero(@RequestBody Hero hero) {
        heroesById.get(hero.getId()).setName(hero.getName());
        return heroesById.get(hero.getId());
    }

    @PostMapping("/api/heroes")
    public void addHero(@RequestBody Hero hero) {
        hero.setId(UUID.randomUUID());
        heroes.add(hero);
        heroesById.putIfAbsent(hero.getId(), hero);
    }
}
