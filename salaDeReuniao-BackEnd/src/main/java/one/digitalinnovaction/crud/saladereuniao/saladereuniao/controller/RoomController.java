package one.digitalinnovaction.crud.saladereuniao.saladereuniao.controller;

import one.digitalinnovaction.crud.saladereuniao.saladereuniao.exception.ResourceNotFoundException;
import one.digitalinnovaction.crud.saladereuniao.saladereuniao.model.Room;
import one.digitalinnovaction.crud.saladereuniao.saladereuniao.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/rooms")
    public List<Room>  getAllRooms(){
        return roomRepository.findAll();
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room>  getRoomById(@PathVariable(value = "id") long roomid) throws ResourceNotFoundException {
            Room room = roomRepository.findById(roomid)
                    .orElseThrow(() -> new ResourceNotFoundException("Room not Found::" + roomid));
            return  ResponseEntity.ok().body(room);
        }

    @PostMapping("/rooms")
    public Room createRoom(@Valid @RequestBody Room room){
        return roomRepository.save(room);
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<Room>  updateRoom(@PathVariable(value = "id") long roomid, @Valid @RequestBody Room roomDetails) throws ResourceNotFoundException {
        Room room = roomRepository.findById(roomid)
                .orElseThrow(() -> new ResourceNotFoundException("Room not Found this id ::" + roomid));
        room.setName(roomDetails.getName());
        room.setDate(roomDetails.getDate());
        room.setStartHour(roomDetails.getStartHour());
        room.setEndHour(roomDetails.getEndHour());
        final Room updateRoom = roomRepository.save(room);
        return ResponseEntity.ok(updateRoom);

    }

    @DeleteMapping("/rooms/{id}")
    public Map<String,Boolean> deleteRoom(@PathVariable(value = "id") long roomid) throws ResourceNotFoundException {
        Room room = roomRepository.findById(roomid)
                .orElseThrow(() -> new ResourceNotFoundException("Room not Found this id ::" + roomid));
                roomRepository.delete(room);
                Map<String,Boolean> response =  new HashMap<>();
                response.put("deleted",Boolean.TRUE);
                return response;
    }
}
