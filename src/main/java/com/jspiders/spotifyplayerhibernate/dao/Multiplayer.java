package com.jspiders.spotifyplayerhibernate.dao;

import java.util.InputMismatchException;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.TransactionException;

import com.jspiders.spotifyplayerhibernate.dto.SongOperation;

public class Multiplayer {

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	private static EntityTransaction entityTransaction;

	static SongOperation songOperation = new SongOperation();
	static Scanner sc = new Scanner(System.in);
	static boolean loop = true;

//	public static void main(String[] args) {
////		songOperation.addSongs();
////		songOperation.playList();
////		songOperation.removeSong();
////		songOperation.updateSong();
////		songOperation.playAllSongs();
////		songOperation.chooseSongToPlay();
//		songOperation.playRandomSong();
//		
//	}
//}

	public static void main(String[] args) {
		openConnections();
		entityTransaction.begin();


		Multiplayer obj = new Multiplayer();
		try {
			while (loop) {
				obj.menu();
			}
		} catch (InputMismatchException e) {
			System.out.println("You entered something wrong.");
		}

		entityTransaction.commit();

		closeConnectios();
	}

	public void menu() {
		System.out.println("---------------------Welcome to Spotify player--------------------");
		System.out.println("1.Play song\n2.Add/Remove song\n3.Edit song\n4.Show playList\n5.Exit");
		int a = sc.nextInt();

		switch (a) {
		case 1:
			play();
			break;
		case 2:
			add_remove();
			break;
		case 3:
			edit();
			break;
		case 4:
			songOperation.playList();
			break;
		case 5:
			System.out.println("Thanks for visiting....");
			loop = false;
			break;
		default:
			System.out.println("Enter valid input");
		}
	}

	public void play() {
		System.out.println("------------Choose any one---------------");
		System.out.println("1.Choose song\n2.All songs\n3.Random song\n4.Go back");
		int a = sc.nextInt();
		switch (a) {
		case 1:
			songOperation.chooseSongToPlay();
			break;
		case 2:
			songOperation.playAllSongs();
			break;
		case 3:
			songOperation.playRandomSong();
			break;
		case 4:
			menu();
			break;

		}
	}

	public void add_remove() {
		System.out.println("------------Choose any one---------------");
		System.out.println("1.add song\n2.remove song\n3.Go back");
		int a = sc.nextInt();
		switch (a) {
		case 1:
			songOperation.addSongs();
			break;
		case 2:
			songOperation.removeSong();
			break;
		case 3:
			menu();
			break;

		}
	}

	public void edit() {
		System.out.println("------------Choose any one---------------");
		System.out.println("1.update song\n2.Go back");
		int a = sc.nextInt();
		switch (a) {
		case 1:
			songOperation.updateSong();
			break;
		case 2:
			menu();
			break;
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
		if (entityTransaction != null) {
			try {
				entityTransaction.rollback();
			} catch (TransactionException e) {
				System.out.println("Transaction can't be rolled out.(Bcz its commited)");
			}
		}
	}

}
