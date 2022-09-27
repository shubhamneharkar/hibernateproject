package com.jspiders.spotifyplayerhibernate.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Song {

	@Id
	private long id;
	private String songName;
	private String singerName;
	private String moviesName;
	private String composer;
	private String lyricist;
	private double length;
	
	@Override
	public String toString() {
		return "id=" + id + ", songName=" + songName;
	}
	
	
}
