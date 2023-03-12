/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package findplayer;

/**
 *
 * @author mohamed mosa
 */
import java.util.Scanner;
import java.awt.Point;

public class Findplayer {
	int length = 0; // number of players
	java.awt.Point[] playersArray = new java.awt.Point[10000]; // list of points representing players
	int currentPlayerArea = 0;
	int[] element = { 0, 0, 0, 0 }; // right left up down

	public void checkPlayer(String[] photo, int team, int X, int Y) {

		if (X + 1 < photo[0].length() && Character.getNumericValue(photo[Y].charAt(X + 1)) == team) {// check right
			if (X + 1 > element[0]) {
				element[0] = X + 1;
			}
			photo[Y] = photo[Y].substring(0, X + 1) + '*' + photo[Y].substring(X + 2);
			this.currentPlayerArea++;// increase current player area
			checkPlayer(photo, team, X + 1, Y);

		}
		if (X - 1 != -1 && Character.getNumericValue(photo[Y].charAt(X - 1)) == team && X - 1 != -1) {// check left
			if (X - 1 < element[1]) {
				element[1] = X - 1;
			}
			photo[Y] = photo[Y].substring(0, X - 1) + '*' + photo[Y].substring(X);
			this.currentPlayerArea++;
			checkPlayer(photo, team, X - 1, Y);

		}
		if (Y - 1 != -1 && Character.getNumericValue(photo[Y - 1].charAt(X)) == team) {// check up
			if (Y - 1 < element[2]) {
				element[2] = Y - 1;
			}
			photo[Y - 1] = photo[Y - 1].substring(0, X) + '*' + photo[Y - 1].substring(X + 1);
			this.currentPlayerArea++;
			checkPlayer(photo, team, X, Y - 1);

		}
		if (Y + 1 < photo.length && Character.getNumericValue(photo[Y + 1].charAt(X)) == team) {// check down
			if (Y + 1 > element[3]) {
				element[3] = Y + 1;
			}
			photo[Y + 1] = photo[Y + 1].substring(0, X) + '*' + photo[Y + 1].substring(X + 1);
			this.currentPlayerArea++;
			checkPlayer(photo, team, X, Y + 1);

		}

	}

	public java.awt.Point Centre() {
		int right = (element[0]);
		int left = element[1];
		int up = element[2];
		int down = (element[3]);
		java.awt.Point C = new java.awt.Point((right + left + 1), (up + down + 1));// calculate center
		return C;
	}

	public java.awt.Point[] findPlayers(String[] photo, int team, int threshold) {

		int MAX;
		if (photo.length == 0) {
			MAX = 0;
		} else {
			MAX = (photo.length * photo[0].length() * 4 / threshold) + 1;
		}
		Point[] arr = new Point[MAX];
		if (photo != null && photo.length != 0 && photo[0] != null) {

			for (int i = 0; i < photo.length; i++) {
				for (int j = 0; j < photo[0].length(); j++) {

					if (Character.getNumericValue(photo[i].charAt(j)) == team) {// photo[i].charAt(j)// in compare only
																				// to be come valiade photo[i] =
																				// photo[i].substring(0,j) + '*' +
																				// photo[i].substring(j+1);
						this.currentPlayerArea++;
						element[0] = element[1] = j;// let x1,x2==j
						element[2] = element[3] = i;// let y1,y2==i
						// check and calculate area
						checkPlayer(photo, team, j, i);
						if (this.currentPlayerArea * 4 >= threshold) {
							// append to this.playersArray if not exist
							arr[length] = Centre();
							this.length++;
						}
					}
					// reset the parameters
					this.currentPlayerArea = 0;
				}
			}
		}
		sortPlayers(arr);
		return arr;
	}

	public void sortPlayers(java.awt.Point[] arr) { // bubble sort
		boolean sorted = true;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length - 1 - i; j++) {
				if (arr[j].getX() > arr[j + 1].getX()
						|| (arr[j].getX() == arr[j + 1].getX() && arr[j].getY() > arr[j + 1].getY())) {
					sorted = false;
					java.awt.Point x = new java.awt.Point(arr[j]);
					arr[j].setLocation(arr[j + 1]);
					arr[j + 1].setLocation(x);
				}
			}
			if (sorted) {
				break;
			}
		}
	}

	public static void main(String[] args) {
		int i = 0, j = 0;
		Scanner in = new Scanner(System.in);
		String sc = new String();
		sc = in.nextLine();
		String[] sin = sc.split(", ");
		int pr, pc;
		pr = Integer.parseInt(sin[0]);
		pc = Integer.parseInt(sin[1]);
		String[] iphoto = new String[pr];
		for (i = 0; i < pr; i++)
			iphoto[i] = in.next();
		for (i = 0; i < pr; i++)
			System.out.print(iphoto[i]);
		int mteam = in.nextInt();
		int mythereshould = in.nextInt();
		Findplayer test = new Findplayer();
		Point[] output = test.findPlayers(iphoto, mteam, mythereshould);
		System.out.print("[");
		for (i = 0; i < output.length; i++) {
			if (output[i] == null)
				break;

			System.out.print("(");
			System.out.print((int) output[i].getX() + "," + (int) output[i].getY());
			System.out.print(")");
		}
		System.out.print("]");
	}

}
