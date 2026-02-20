const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const [N, M] = input[0].split(" ").map(Number);
const board = [];
const start = [-1, -1];
const deliverPoints = [];
for (let i = 0; i < N; i++) {
  const line = input[1 + i].split("");
  board.push(line);
  line.forEach((v, j) => {
    if (v === "S") {
      start[0] = i;
      start[1] = j;
    } else if (v === "C") {
      deliverPoints.push([i, j]);
    }
  });
}

const delta = [
  [-1, 0],
  [0, 1],
  [1, 0],
  [0, -1],
];

class Node {
  constructor(...x) {
    [this.x, this.y, this.direction, this.count, this.state] = x;
  }

  add(node) {
    this.next = node;
  }

  getRear() {
    return this.next;
  }
}

class Queue {
  constructor() {
    this.front = undefined;
    this.rear = undefined;
  }

  push(node) {
    if (this.front === undefined) {
      this.front = node;
      this.rear = node;
    } else {
      this.rear.add(node);
    }
    this.rear = node;
  }

  pop() {
    let ret = this.front;
    if (this.front === this.rear) {
      this.front = undefined;
      this.rear = undefined;
    } else {
      this.front = this.front.getRear();
    }
    return ret;
  }

  isEmpty() {
    if (this.front === undefined) return true;
    return false;
  }
}

let queue = new Queue();
const visited = [...Array(N)].map(() =>
  [...Array(M)].map(() => [...Array(4)].map(() => Array(3).fill(false))),
); // 좌표, 이전방향, 배달 완료 여부(0: x, 1: c1, 2: c2)

for (let d = 0; d < 4; d++) {
  queue.push(new Node(...start, d, 0, 0));
}

let answer = -1;
loop1: while (!queue.isEmpty()) {
  let cur = queue.pop();

  for (let d = 0; d < 4; d++) {
    if (cur.direction === d) continue;

    let [nx, ny] = [cur.x + delta[d][0], cur.y + delta[d][1]];
    if (nx < 0 || nx >= N || ny < 0 || ny >= M) continue;
    if (board[nx][ny] === "#") continue;

    // 배달할 곳인지 확인
    let nextState = cur.state;
    if (board[nx][ny] === "C") {
      let solved = false;
      deliverPoints.forEach((p, i) => {
        let [px, py] = p;
        if (px === nx && py === ny) {
          if (nextState === 0) {
            nextState = i + 1;
          } else if (nextState !== i + 1) {
            // 도착
            answer = cur.count + 1;
            solved = true;
          }
        }
      });
      if (solved) break loop1;
    }
    if (visited[nx][ny][d][nextState]) continue;
    visited[nx][ny][d][nextState] = true;
    queue.push(new Node(nx, ny, d, cur.count + 1, nextState));
  }
}

console.log(answer);
