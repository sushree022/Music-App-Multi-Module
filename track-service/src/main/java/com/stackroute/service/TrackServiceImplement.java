package com.stackroute.service;

import com.stackroute.domain.Track;
import com.stackroute.exceptions.TrackAlreadyExistException;
import com.stackroute.exceptions.TrackNotFoundException;
import com.stackroute.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Profile("prod")
@Service

//Primary annotation is used to give higher preference to a bean when there are multiple beans of the same type.
@Primary
public class TrackServiceImplement implements TrackService {
    private TrackRepository trackRepository;

    //    constructor
    @Autowired
    public TrackServiceImplement(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    /*    to save a track
     and to throw exception
     */
    @Override
    public Track saveTrack(Track track) throws TrackAlreadyExistException {
        if (trackRepository.existsById(track.getId())) {
            throw new TrackAlreadyExistException("Track is already present");
        }
        Track saveTrack = trackRepository.save(track);
        return saveTrack;
    }

    //    to retrieve list of tracks
    @Override
    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    /*    to retrieve a track by its id
     and to throw exception
     */
    @Override
    public Track getTrackById(int id) throws TrackNotFoundException {
        if (!trackRepository.existsById(id)) {
            throw new TrackNotFoundException("Track with given ID does not exist");
        }
        return trackRepository.findById(id).get();
    }

    /*   to delete a track by its id
   and to throw exception
     */
    @Override
    public Track deleteById(int id) throws TrackNotFoundException {
        if (!trackRepository.existsById(id)) {
            throw new TrackNotFoundException("Track with given ID does not exist");
        }
        Optional<Track> optionalTrack = trackRepository.findById(id);
        trackRepository.deleteById(id);
        return optionalTrack.get();
    }

    /*    to update a part of a track by its id
    and to throw exception
     */
    @Override
    public Track updateTrackbyId(int id, Track track) throws TrackNotFoundException {
        if (!trackRepository.existsById(id)) {
            throw new TrackNotFoundException("Track which to be updated not found");
        }
//        delete the track
        trackRepository.deleteById(id);
//        edit the track and save it
        Track updateTrack = trackRepository.save(track);
        return updateTrack;
    }

    /*   to retrieve a track by its name
    and to throw exception
     */
    @Override
    public Track searchTrackByName(String name) throws TrackNotFoundException {
        Track retrieveTrack = trackRepository.findByName(name);
        if (retrieveTrack != null) {
            return retrieveTrack;
        } else throw new TrackNotFoundException("This track with provided name does not exist");
    }
}
