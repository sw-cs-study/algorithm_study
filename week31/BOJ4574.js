/**
 * visited로 사용가능한 도미노 짜르기 => 현재 칸에서 사용할 도미노가 이미 사용됐다면 안하고 넘어가려고 => 숫자 두개의 조합으로 visited 처리(순서 달라도 같은 거로 처리)
 * 스도쿠 관련 함수 만들어야함
 * 1. 세로 줄을 보고 현재 칸에서 가능한 숫자 리스트 생성
 * 2. 가로 줄을 보고 현재 칸에서 가능한 숫자 리스트 생성
 * 3. 9칸을 보고 현재 칸에서 가능한 숫자 리스트 생성
 *
 * 전체 플로우
 * 1. 빈칸에서 인접한 두칸을 설정하고 그 칸에 들어갈 수 있는 수로 채움
 * -> 인접한 두칸이 없는 경우 현재 경우 fail
 * -> 들어갈 수가 하나라도 없는 경우 현재 경우 fail
 * 2. 현재 칸을 채웠다면 그 다음 자리 찾기
 */

const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

let IDX = 0;
let puzzleCount = 1;
let printer = "";
while (true) {
  let n = Number(input[IDX]);
  if (n === 0) break;
  printer += `\nPuzzle ${puzzleCount++}`;

  IDX += n + 1;
}
console.log(printer);

/**
 * 다 채운 보드 리턴
 * @param {*} filledDominoNumber 채워져있는 도미노의 갯수
 * @param {*} dominoPoses 채워져있는 도미노의 위치
 * @param {*} numberPoses 채워져있는 숫자의 위치(1~9순서)
 */
function solve(filledDominoNumber, dominoPoses, numberPoses) {}

// 수직줄에서 가능한 수
function getVerticalResult(x, board) {
  const existing = Array(10).fill(false);
  for (let j = 0; j < 9; j++) {
    existing[board[x][j]] = true;
  }
  const ret = [];
  for (let i = 1; i < existing.length; i++) {
    if (existing[i]) {
      continue;
    }
    ret.push(i);
  }
  return ret;
}

// 수평줄에서 가능한 수
function getHorizontalResult(y, board) {
  const existing = Array(10).fill(false);
  for (let i = 0; i < 9; i++) {
    existing[board[i][y]] = true;
  }
  const ret = [];
  for (let i = 1; i < existing.length; i++) {
    if (existing[i]) {
      continue;
    }
    ret.push(i);
  }
  return ret;
}

// 3*3에서 가능한 수
function getSectionResult(x, y, board) {
  const existing = Array(10).fill(false);
  const xFiller = Math.floor(x / 3);
  const yFiller = Math.floor(y / 3);
  for (let i = 0; i < 3; i++) {
    for (let j = 0; j < 3; j++) {
      existing[board[xFiller + i][yFiller + j]] = true;
    }
  }
  const ret = [];
  for (let i = 1; i < existing.length; i++) {
    if (existing[i]) {
      continue;
    }
    ret.push(i);
  }
  return ret;
}
