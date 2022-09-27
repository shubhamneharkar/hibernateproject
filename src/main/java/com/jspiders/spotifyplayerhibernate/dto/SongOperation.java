package com.jspiders.spotifyplayerhibernate.dto;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class SongOperation {

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	private static EntityTransaction entityTransaction;
	private static Scanner sc = new Scanner(System.in);
	private static long max;
	private static List<Long> list = new ArrayList<>();

	public void removeSong() {
		playList();
		openConnections();
		entityTransaction.begin();
		System.out.println("Enter id ");
		Song songObject = entityManager.find(Song.class, sc.nextInt());
		System.out.println(songObject.getSongName() + " Song removed successfully.");
		entityManager.remove(songObject);

		entityTransaction.commit();
		closeConnectios();
	}

	public void playRandomSong() {

		openConnections();
		entityTransaction.begin();

		String jpql = " select id from Song";
		Query query = entityManager.createQuery(jpql);
		List songs = query.getResultList();

		for (Object object : songs) {
			list.add((long) object);
		}

		Random rand = new Random();
		Long newrandom = rand.nextLong(list.get(list.size() - 1));
		newrandom++;
//		System.out.println("Newly generated number from list " + newrandom);

//		System.out.println("List ele 1 " + list);
		int a = 0;
		Song newSongObject = entityManager.find(Song.class, newrandom);
		System.out.println(newSongObject.getId() + ". now playing " + newSongObject.getSongName());

		entityTransaction.commit();
		closeConnectios();
	}

	public void playList() {
		openConnections();
		entityTransaction.begin();

		String jpql = " select id from Song";
		Query query = entityManager.createQuery(jpql);
		List songs = query.getResultList();

		int a = 0;
		for (Object song : songs) {
			Song newSongObject = entityManager.find(Song.class, song);
			System.out.println(++a + ".  " + newSongObject.toString());
//			System.out.println(newSongObject.hashCode());

		}

		entityTransaction.commit();
		closeConnectios();
	}

	public void chooseSongToPlay() {
		playList();

		openConnections();
		entityTransaction.begin();

		System.out.println("Enter id to play song ");
		Song song = entityManager.find(Song.class, sc.nextLong());
		System.out.println("now playing " + song.getSongName());

		entityTransaction.commit();
		closeConnectios();
	}

	public void playAllSongs() {
		openConnections();
		entityTransaction.begin();

		String jpql = "select songName from Song";
		Query query = entityManager.createQuery(jpql);
		List resultList = query.getResultList();

		for (Object object : resultList) {
			System.out.println("now playing " + object);
		}

		entityTransaction.commit();
		closeConnectios();
	}

	public void updateSong() {
		playList();
		openConnections();
		entityTransaction.begin();

		System.out.println("Enter song id to update that Song ");
		Song reqSongObj = entityManager.find(Song.class, sc.nextLong());
		reqSongObj.setSongName(sc.next());
		System.out.println("successfully updated.");

		entityTransaction.commit();
		closeConnectios();

	}

	public void addSongs() {
		try {
			System.out.println("How many songs you want to add.");
			int a = sc.nextInt();
			while (a > 0) {
				openConnections();
				entityTransaction.begin();
				Song song = new Song();

				System.out.println("Enter id");
				song.setId(sc.nextLong());

				System.out.println("Enter songName" + "\n");
				song.setSongName(sc.next());
				System.out.println("Enter singerName");
				song.setSingerName(sc.next());
				System.out.println("Enter composerName");
				song.setComposer(sc.next());
				System.out.println("Enter lyricist");
				song.setLyricist(sc.next());
				System.out.println("Enter moviesName");
				song.setMoviesName(sc.next());
				System.out.println("Enter length");
				song.setLength(sc.nextDouble());

				entityManager.persist(song);

				entityTransaction.commit();

				closeConnectios();
				a--;
			}
		} catch (InputMismatchException e) {
			System.out.println("entered invalid input");
		}
	}

	private static void openConnections() {
		entityManagerFactory = Persistence.createEntityManagerFactory("spotifyplayerhibernate");
		entityManager = entityManagerFactory.createEntityManager();
		entityTransaction = entityManager.getTransaction();
	}

	private static void closeConnectios() {
		if (entityManagerFactory != null) {
			entityManagerFactory.close();
		}
		if (entityManager != null) {
			entityManager.close();
		}
	}

}
