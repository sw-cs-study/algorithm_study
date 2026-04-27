const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const [N, M] = input[0].split(" ").map(Number);
const board = [];
for (let i = 0; i < N; i++) {
  board.push(input[1 + i].split(""));
}

console.log(bfs());

function bfs() {
  const delta = [
    [0, -1],
    [0, 1],
    [-1, 0],
    [1, 0],
  ];

  let [x, y] = [-1, -1];
  for (let i = 0; i < N; i++) {
    for (let j = 0; j < M; j++) {
      if (board[i][j] === "0") {
        [x, y] = [i, j];
        break;
      }
    }
    if (x >= 0) break;
  }

  const visited = [...Array(N)].map(() =>
    [...Array(M)].map(() => Array(1 << 6).fill(Infinity)),
  );
  visited[x][y][0] = 0;

  const keys = {};
  for (let i = 0; i < 6; i++) {
    keys[String.fromCharCode("a".charCodeAt() + i)] = 1 << i;
  }
  const doors = {};
  for (let i = 0; i < 6; i++) {
    doors[String.fromCharCode("A".charCodeAt() + i)] = 1 << i;
  }

  const queue = [[x, y, 0, 0]];
  while (queue.length > 0) {
    let [x, y, dist, state] = queue.shift();

    for (let [dx, dy] of delta) {
      let [nx, ny] = [x + dx, y + dy];
      if (!check(nx, ny) || board[nx][ny] === "#") continue;
      if (visited[nx][ny][state] !== Infinity) continue;
      visited[nx][ny][state] = true;
      let v = board[nx][ny];
      let newState = state;
      if (v === "1") {
        return dist + 1;
      } else if (keys[v] !== undefined) {
        // key
        newState = state | keys[v];
      } else if (doors[v] !== undefined) {
        // door
        if ((state & doors[v]) === 0) continue;
      }
      queue.push([nx, ny, dist + 1, newState]);
    }
  }

  return -1;
}

function check(x, y) {
  if (x < 0 || x >= N) return false;
  if (y < 0 || y >= M) return false;
  return true;
}
