package baekjoon.week23;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 구현 + 완탐
 *
 * 주어진 스티커를 왼쪽 맨위부터 붙여가면서, 붙일수 있으면 다음 스티커로 넘어가고, 안된다면 회전까지 하면서 붙여본다.
 * 빈 행,열이 있는지, 스티커가 겹치지는 않았는지, 모눈종이를 벗어나지는 않았는지를 확인하면 된다.
 *
 */

public class BOJ18808_스티커붙이기 {


	//노트북의 가로 세로
	private static int N;
	private static int M;

	//노트북 상태.
	private static int[][] notebook;

	//스티커 상태
	private static int[][] sticker;


	//회전 - 현재 스티커를 90도 회전(0 -> 90 -> 180 -> 270 순서대로 회전하기 떄문에 90도씩만 회전하면 됨.)
	private static void rotate(){

		int[][] temp = new int[sticker[0].length][sticker.length];
		for (int i = 0; i < sticker.length; i++){
			for (int j = 0; j < sticker[0].length; j++){

				temp[j][temp[0].length - i - 1] = sticker[i][j];
			}
		}

		sticker = temp;
	}

	//스티커를 노트북 상태에 둘 수 있는지. - 가장 왼쪽맨위 좌표를 기준점으로 받음.
	//true 일 경우는 스티커를 붙임.
	private static boolean check(int stdX, int stdY){

		for (int i = stdX; i < stdX + sticker.length; i++){
			for (int j = stdY; j < stdY + sticker[0].length; j++){

				//노트북 좌표를 벗어나면 종료
				if (i >= N || j >= M) return false;

				//기존에 스티커가 있다면 종료.
				if(notebook[i][j] == 1 && sticker[i - stdX][j - stdY] == 1) return false;

			}
		}


		return true;
	}

	//스티커 배치 - 스티커 붙일수 있는지 확인하는 부분에서 스티커를 붙이면, 붙일수 없는 상태가 되었을때 원복해야 함.
	private static void stickerSetting(int stdX, int stdY){
		for (int i = stdX; i < stdX + sticker.length; i++){
			for (int j = stdY; j < stdY + sticker[0].length; j++){

				if(sticker[i - stdX][j - stdY] != 1) continue;

				notebook[i][j] = 1;
			}
		}
	}

	//칸수 세기
	private static int counting(){
		int count = 0;

		for(int i = 0; i < N; i++){
			for(int j = 0; j < M; j++){

				if(notebook[i][j] == 0) continue;

				count++;
			}
		}


		return count;
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		notebook = new int[N][M];


		for (int i = 0; i < K; i++){
			st = new StringTokenizer(br.readLine());

			int R = Integer.parseInt(st.nextToken());
			int C = Integer.parseInt(st.nextToken());

			sticker = new int[R][C];

			for(int a = 0; a < R; a++){
				st = new StringTokenizer(br.readLine());

				for(int b = 0; b < C; b++){
					sticker[a][b] = Integer.parseInt(st.nextToken());
				}
			}

			// 0 -> 90 -> 180 -> 270 총 4개의 상태가 있음.
			loop:
			for(int z = 0; z < 4; z++) {

				//노트북의 맨 왼쪽 위부터 두기 시작.
				for (int a = 0; a < N; a++) {
					for (int b = 0; b < M; b++) {

						//둘 수 있다면 두고 종료.
						if (check(a, b)){
							stickerSetting(a, b);
							break loop;
						}


					}
				}

				//회전
				rotate();
			}
		}

		//최종 칸수 세기.
		System.out.println(counting());
	}
}
