const input = require("fs")
  .readFileSync(
    process.platform === "linux" ? "/dev/stdin" : "../../sample.txt"
  )
  .toString()
  .trim()
  .split("\n");

const [n, m, k] = input[0].split(" ").map(Number);
const map = [];
for (let i = 0; i < n; i++) {
  map.push(input[1 + i].split("").map(Number));
}
const delta = [
  [1, 0],
  [-1, 0],
  [0, 1],
  [0, -1],
];

const queue = [[0, 0, k]]; // 좌표, 뚫을 수 있는 벽

// 방문 처리는 각 위치별로 지날때 벽을 부수는 횟수가 몇번 남았는지를 인지를 체크 => 해당 점을
const visited = [...Array(n)].map(() =>
  [...Array(m)].map(() => Array(k + 1).fill(false))
);
visited[0][0][k] = true;

let answer = -1;
let day = 1;
loop1: while (queue.length > 0) {
  let size = queue.length;
  for (let i = 0; i < size; i++) {
    let [x, y, canBreak] = queue.shift();
    if (x === n - 1 && y === m - 1) {
      answer = day;
      break loop1;
    }
    // 이동로직
    let stayed = false;
    for (let [dx, dy] of delta) {
      let [nx, ny] = [x + dx, y + dy];
      if (nx < 0 || nx >= n || ny < 0 || ny >= m) continue;
      // 밤일 때 대기 => 이 로직이 유의미하려면 부술수 있는 횟수가 있어야함
      if (map[nx][ny] === 1 && canBreak > 0 && day % 2 === 0 && !stayed) {
        queue.push([x, y, canBreak]);
        stayed = true;
      } else if (
        map[nx][ny] === 1 &&
        canBreak > 0 &&
        day % 2 === 1 &&
        !visited[nx][ny][canBreak - 1]
      ) {
        queue.push([nx, ny, canBreak - 1]);
        visited[nx][ny][canBreak - 1] = true;
      } else if (map[nx][ny] === 0 && !visited[nx][ny][canBreak]) {
        queue.push([nx, ny, canBreak]);
        visited[nx][ny][canBreak] = true;
      }
    }
  }
  day++;
}
console.log(answer);
