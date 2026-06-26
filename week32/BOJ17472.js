const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const [n, m] = input[0].split(" ").map(Number);
const board = [];
for (let i = 0; i < n; i++) {
  board.push(input[1 + i].split(" ").map(Number));
}

const delta = [
  [-1, 0],
  [1, 0],
  [0, 1],
  [0, -1],
];

// 섬 구분하기
let islandNumber = 1; // 1번 섬부터 존재
let visited = [...Array(n)].map(() => Array(m).fill(false));
for (let i = 0; i < n; i++) {
  for (let j = 0; j < m; j++) {
    if (visited[i][j]) continue;
    if (board[i][j] === 1) {
      // 새로운 섬 찾으면 같은 번호로 바꾸기
      let queue = [[i, j]];
      board[i][j] = islandNumber;
      visited[i][j] = true;
      while (queue.length > 0) {
        let [x, y] = queue.shift();
        for (let [dx, dy] of delta) {
          let [nx, ny] = [x + dx, y + dy];
          if (
            0 <= nx &&
            nx < n &&
            0 <= ny &&
            ny < m &&
            !visited[nx][ny] &&
            board[nx][ny] === 1
          ) {
            visited[nx][ny] = true;
            board[nx][ny] = islandNumber;
            queue.push([nx, ny]);
          }
        }
      }
      // 섬 하나 끝나면 섬 번호 올리기
      islandNumber++;
    }
  }
}

// 가능한 다리 다 구하기, 가로 10줄과 세로 10줄을 하나씩 지나가며 연결되어 있는거 다 카운트
const edges = [...Array(islandNumber + 1)].map(() =>
  Array(islandNumber + 1).fill(Infinity)
);
// 세로 먼저
for (let i = 0; i < n; i++) {
  let prevIsland = -1;
  let prevJ = -1;
  for (let j = 0; j < m; j++) {
    if (board[i][j] > 0) {
      if (prevJ < 0) {
        // 처음으로 섬을 발견하면 섬번호 갱신하고, 위치도 갱신
        prevIsland = board[i][j];
        prevJ = j;
      }
    }
  }
}

const bridges = [];
