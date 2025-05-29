/**
 * 6각형 보드판을 설계해야함
 * 012
 * 012  => 를 육각형으로 생각
 * board[i][j] 일 때, (i, j)가 (짝, 짝) 일때 위로 못가고 (짝, 홀) 일때 아래로 못감
 * dfs로 하면 경우의 수가 지수적으로 증가함, 근데 2^22라 4*10^6정도라 괜춘
 * 돌아서 지나갔는지 바로 이전길로 돌아갔는지를 판단못함 => depth 차이가 1이면 바로 이전에 지나간길
 */

const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const n = Number(input[0]);
const board = [...Array(2 * n + 1)].map(() => Array(2 * n + 1).fill(0)); // 지나갔으면 현재 depth
const MAX_LENGTH = 2 * n + 1;
const delta = [
  [-1, 0],
  [1, 0],
  [0, -1],
  [0, 1],
];
console.log(dfs() / 3);

function dfs(x = n, y = n, depth = n) {
  if (depth === -1) {
    // 첫 번째 점을 지나치고 세야해서
    if (board[x][y] > 0) return 1;

    return 0;
  }
  if (board[x][y] > 0) return 0;
  board[x][y] = depth;

  let ret = 0;
  // 갈수 있는 방향 탐색
  for (let [dx, dy] of delta) {
    if (dx === 1 && dx !== check(x, y)) continue;
    if (dx === -1 && dx !== check(x, y)) continue;
    [nx, ny] = [x + dx, y + dy];
    if (nx < 0 || nx >= MAX_LENGTH || ny < 0 || ny >= MAX_LENGTH) continue;
    if (board[nx][ny] - board[x][y] === 1) continue; // 바로 이전에 방문한 지점
    ret += dfs(nx, ny, depth - 1);
  }
  board[x][y] = 0;
  return ret;
}

// 위로 이동 가능하면 -1, 아래로 이동 가능하면 1
function check(x, y) {
  if (x % 2 === 0) {
    if (y % 2 === 0) return 1;
    else return -1;
  } else {
    if (y % 2 === 0) return -1;
    else return 1;
  }
}
