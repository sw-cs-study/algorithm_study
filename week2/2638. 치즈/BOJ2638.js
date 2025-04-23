class Node {
  constructor(value) {
    this.value = value;
    this.next = null;
  }
  connect(node) {
    this.next = node;
  }
  getNextNode() {
    return this.next;
  }
  getValue() {
    return this.value;
  }
}

class Queue {
  constructor(...args) {
    this.start = null;
    this.end = this.start;
    this.size = 0;
    if (args.length > 0) {
      for (let v of args) {
        this.enqueue(v);
      }
    }
  }
  enqueue(value) {
    let node = new Node(value);
    if (this.start === null) {
      this.start = node;
      this.end = this.start;
      this.size += 1; // 이 부분이 빠졌었음
      return;
    }
    this.end.connect(node);
    this.end = node;
    this.size += 1;
  }
  dequeue() {
    let ret = this.start.getValue();
    this.start = this.start.getNextNode();
    this.size -= 1;
    return ret;
  }
  isEmpty() {
    if (this.size === 0) { return true; }
    return false;
  }
  getSize() {
    return this.size;
  }
}

const input = require('fs').readFileSync(process.platform === "linux" ? '/dev/stdin' : 'sample.txt').toString().trim().split('\n');

const [n, m] = input[0].split(" ").map(Number);
const board = [];
const isOuter = [];
const cheeze = new Queue();
for (let i = 0; i < n; i++) {
  board.push(input[1 + i].split(" ").map(Number));
  isOuter.push([]);
  for (let j = 0; j < m; j++) {
    isOuter[i].push(false);
    if (board[i][j] === 1) {
      cheeze.enqueue([i, j]);
    }
  }
}
const delta = [[0, 1], [0, -1], [1, 0], [-1, 0]]
outerCheck(0, 0);
let cnt = 0;
const toRemove = []; // 없어질 치즈들
let outerSide;
while (!cheeze.isEmpty()) {
  cnt += 1
  // 사라질 치즈 찾기
  let size = cheeze.getSize();
  for (let k = 0; k < size; k++) {
    let [i, j] = cheeze.dequeue();
    outerSide = 0;
    for (let [di, dj] of delta) {
      let [ni, nj] = [i + di, j + dj];
      if (0 <= ni && ni < n && 0 <= nj && nj < m && isOuter[ni][nj]) {
        outerSide += 1;
      }
    }
    if (outerSide >= 2) {
      toRemove.push([i, j]);
    } else {
      cheeze.enqueue([i, j]);
    }
  }
  // 사라질 치즈 제거 후 외부환경 다시 산정
  while (toRemove.length > 0) {
    let [x, y] = toRemove.pop();
    board[x][y] = 0;
    outerCheck(x, y);
  }
}

console.log(cnt);


function outerCheck(x, y) {
  let q = new Queue([x, y]);
  isOuter[x][y] = true;
  while (!q.isEmpty()) {
    let [i, j] = q.dequeue();
    for (let [di, dj] of delta) {
      let [ni, nj] = [i + di, j + dj];
      if (0 <= ni && ni < n && 0 <= nj && nj < m && board[ni][nj] === 0 && !isOuter[ni][nj]) {
        isOuter[ni][nj] = true;
        q.enqueue([ni, nj]);
      }
    }
  }
}
