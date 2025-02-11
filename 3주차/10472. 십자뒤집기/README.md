### 풀이

알고리즘

1. 3 * 3이라 9비트 패턴으로 만들어서 연산

코드

```jsx
const input = require('fs').readFileSync(process.platform === "linux" ? '/dev/stdin' : 'sample.txt').toString().trim().split('\n');

const p = Number(input[0]);
for (let t = 0; t < p; t++) {
  // 9비트 숫자만들기
  let pattern = 0;
  for (let i = 0; i < 3; i++) {
    for (let j = 0; j < 3; j++) {
      if (input[1 + (t * 3) + i][j] === '*') { pattern += 1 << ((3 * i) + j); }
    }
  }
  // bfs로 9칸 건들기
  const visited = Array.from(Array(1 << 9), () => false);
  const queue = [0];
  visited[0] = true;
  let cnt = 0;

  loop1: while (queue.length > 0) {
    let size = queue.length;
    for (let i = 0; i < size; i++) {
      let v = queue.shift();
      if (v === pattern) { break loop1; } // pattern 찾으면 끝

      for (let point = 0; point < 9; point++) { // 0~8
        let result = flip(v, point);
        if (visited[result]) { continue; }
        visited[result] = true;
        if (result === pattern) { // 맞췄으면 종료
          cnt += 1;
          break loop1;
        }
        queue.push(result);
      }
    }
    cnt += 1;
  }

  console.log(cnt);
}

function flip(board, point) {
  const delta = [[-1, 0], [1, 0], [0, 1], [0, -1], [0, 0]];

  let [x, y] = [Math.floor(point / 3), point % 3];
  for (let [dx, dy] of delta) {
    let [nx, ny] = [x + dx, y + dy];
    if (0 <= nx && nx < 3 && 0 <= ny && ny < 3) {
      let v = 1 << ((3 * nx) + ny);
      if (board & v) { board -= v; } // 1 -> 0
      else { board += v; } // 0 -> 1
    }
  }

  return board;
}
```