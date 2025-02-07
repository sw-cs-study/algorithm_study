### 풀이(시간 초과)

알고리즘

1. 방문 배열을 알파벳으로 관리함. 재귀 dfs

시간복잡도

1. 4^(20*20) ⇒ 터짐

코드

```jsx
const input = require('fs').readFileSync(process.platform === "linux" ? '/dev/stdin' : 'sample.txt').toString().trim().split('\n');

const [r, c] = input[0].split(" ").map(Number);
const board = [];
for (let i = 0; i < r; i++) {
  board.push(input[1 + i].trim());
}
const delta = [[-1, 0], [1, 0], [0, 1], [0, -1]];

visited = {};

console.log(recur());

function recur(x = 0, y = 0) {
  if (visited[board[x][y]]) { return 0; }

  visited[board[x][y]] = true;

  let ret = 0;
  for (let [dx, dy] of delta) {
    let [nx, ny] = [x + dx, y + dy];
    if (0 <= nx && nx < r && 0 <= ny && ny < c && visited[board[nx][ny]] === undefined) {
      ret = Math.max(ret, recur(nx, ny));
    }
  }
  delete visited[board[x][y]];

  return 1 + ret;
}
```

### 풀이2

알고리즘

1. 재귀 dfs인데 memo가 필요함 
2. 근데 알파벳 26자리라 최대 길이 26임 ⇒ 이걸루 가자

코드

```jsx
const input = require('fs').readFileSync(process.platform === "linux" ? '/dev/stdin' : 'sample.txt').toString().trim().split('\n');

const [r, c] = input[0].split(" ").map(Number);
const board = [];
for (let i = 0; i < r; i++) {
  board.push(input[1 + i].trim());
}
const delta = [[-1, 0], [1, 0], [0, 1], [0, -1]];

const visited = {};
let allStop = false;

console.log(recur());

function recur(x = 0, y = 0) {
  if (allStop) { return 0; }
  if (visited[board[x][y]]) { return 0; }

  visited[board[x][y]] = true;

  let ret = 0;
  for (let [dx, dy] of delta) {
    let [nx, ny] = [x + dx, y + dy];
    if (0 <= nx && nx < r && 0 <= ny && ny < c && !visited[board[nx][ny]]) {
      ret = Math.max(ret, recur(nx, ny));
      if (ret === 25) {
        allStop = true;
        return 26;
      }
    }
  }
  visited[board[x][y]] = false;

  return 1 + ret;
}
```