package me.indychkov.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AnimalController {

    @Autowired
    AnimalRepository mAnimalRepository;
    @GetMapping("/")
    public String getString() {
        return "Hello, its CSA project pet shop in Docker by Ilya Dychkov IKBO-04-17";
    }
    @GetMapping("/animals")
    public List getAllNotes() {

        return mAnimalRepository.findAll();
    }

    @PostMapping("/animals")
    public Animal createNote(@Valid @RequestBody Animal animal) {
        return mAnimalRepository.save(animal);
    }

    @GetMapping("/animals/id={id}")
    public Animal getNoteById(@PathVariable(value = "id") Integer id) throws AnimalNotFoundException {
        return mAnimalRepository.findById(id)
                .orElseThrow(() -> new AnimalNotFoundException(id));
    }

    @GetMapping("/animals/kind={kind}")
    public List<Animal> getByType(@PathVariable(value = "kind") String kind) {
        return mAnimalRepository.findAllByKind(kind);
    }

    @GetMapping("/animals/name={name}")
    public List<Animal> getByName(@PathVariable(value = "name") String name) {
        return mAnimalRepository.findAllByNameContaining(name);
    }

    @PutMapping("/animals/id={id}")
    public Animal updateNote(@PathVariable(value = "id") Integer id,
                             @Valid @RequestBody Animal animalDetails) throws AnimalNotFoundException {

        Animal animal = mAnimalRepository.findById(id)
                .orElseThrow(() -> new AnimalNotFoundException(id));

        animal.setName(animalDetails.getName());
        animal.setKind(animalDetails.getKind());
        animal.setPrice(animalDetails.getPrice());
        return mAnimalRepository.save(animal);
    }

    @DeleteMapping("/animals/{id}")
    public ResponseEntity deleteAnimal(@PathVariable(value = "id") Integer id) throws AnimalNotFoundException {
        Animal animal = mAnimalRepository.findById(id)
                .orElseThrow(() -> new AnimalNotFoundException(id));
        mAnimalRepository.delete(animal);
        return ResponseEntity.ok().build();
    }
}
