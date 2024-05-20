package com.springBase.demo.controller;

import com.springBase.demo.entities.Car;
import com.springBase.demo.repo.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @PostMapping
    public Car createCar(@RequestBody Car car) {
        return carRepository.save(car);
    }

    @GetMapping
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        if (carRepository.existsById(id)) {
            return ResponseEntity.ok(carRepository.findById(id).orElse(null));
        } else {
            return ResponseEntity.ok(new Car());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCarType(@PathVariable Long id, @RequestParam String type) {
        if (carRepository.existsById(id)) {
            Car car = carRepository.findById(id).orElse(null);
            if (car != null) {
                car.setType(type);
                return ResponseEntity.ok(carRepository.save(car));
            }
        }
        return ResponseEntity.ok(new Car());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable Long id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
            return ResponseEntity.ok("Car deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Car not found");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllCars() {
        carRepository.deleteAll();
        return ResponseEntity.ok("All cars deleted successfully");
    }
}
