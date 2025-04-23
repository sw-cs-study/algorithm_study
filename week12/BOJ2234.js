// 성곽
const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const [m, n] = input[0].split(" ").map(Number);
const board = [];
for (let i = 1; i < n + 1; i++) {
  board.push(input[i].split(" ").map(Number));
}
const visited = [...Array(n)].map(() => Array(m).fill(false));
const roomInfoBoard = [...Array(n)].map(() => Array(m).fill(0));
const roomSizeArr = [0];

let maxSize = 0;
let roomCount = 0;
const delta = [
  [0, -1],
  [-1, 0],
  [0, 1],
  [1, 0],
];
for (let idx = 0; idx < n * m; idx++) {
  let [x, y] = [Math.floor(idx / m), idx % m];
  if (visited[x][y]) continue;
  roomCount++;
  const queue = [[x, y]];
  visited[x][y] = true;
  let cnt = 0;
  while (queue.length > 0) {
    const [x, y] = queue.shift();
    roomInfoBoard[x][y] = roomCount;
    cnt++;
    for (let i = 0; i < 4; i++) {
      // left, up, right, down
      if (board[x][y] & (1 << i)) continue; // wall
      let [nx, ny] = [x + delta[i][0], y + delta[i][1]];
      if (nx < 0 || nx >= n || ny < 0 || ny >= m) continue; // out of bounds
      if (visited[nx][ny]) continue; // already visited
      visited[nx][ny] = true;
      queue.push([nx, ny]);
    }
  }
  if (cnt > maxSize) {
    maxSize = cnt;
  }
  roomSizeArr.push(cnt);
}

// 각 번호별 인접한 방을 인접행렬로 저장
const adj = [...Array(roomCount + 1)].map(() =>
  [...Array(roomCount + 1)].fill(false)
);
for (let i = 0; i < n; i++) {
  for (let j = 0; j < m; j++) {
    for (let k = 0; k < 4; k++) {
      if (!(board[i][j] & (1 << k))) continue;
      let [nx, ny] = [i + delta[k][0], j + delta[k][1]];
      if (nx < 0 || nx >= n || ny < 0 || ny >= m) continue;
      if (roomInfoBoard[i][j] === roomInfoBoard[nx][ny]) continue;
      adj[roomInfoBoard[i][j]][roomInfoBoard[nx][ny]] = true;
      adj[roomInfoBoard[nx][ny]][roomInfoBoard[i][j]] = true;
    }
  }
}
// 각 인접한 두 방의 크기를 더한 값 중 최댓값
let maxAdjSize = 0;
for (let i = 1; i < roomCount + 1; i++) {
  for (let j = i + 1; j < roomCount + 1; j++) {
    if (!adj[i][j]) continue;
    if (roomSizeArr[i] + roomSizeArr[j] > maxAdjSize) {
      maxAdjSize = roomSizeArr[i] + roomSizeArr[j];
    }
  }
}

console.log(`${roomCount}\n${maxSize}\n${maxAdjSize}`);
