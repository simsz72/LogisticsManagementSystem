package com.truckersmpspringboot.repository;

import com.truckersmpspringboot.model.Checkpoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckpointRepository extends JpaRepository<Checkpoint,Integer> {
    Checkpoint findCheckpointByCheckpointAddress(String checkpointAddress);
}
