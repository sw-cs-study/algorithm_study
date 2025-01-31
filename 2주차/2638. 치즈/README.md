### 풀이

알고리즘

1. 외부 환경인지 아닌지 판별하는 배열을 생성. 외부 환경 파악. 치즈에 닿아있는 좌표 저장(큐 써야 할 듯)
2. 따로 방문 배열 있어야함. 치즈 위치 큐에 넣으면서 돌기(두 변이 닿아있는 치즈 위치 배열에 따로 저장), 치즈 위치를 꺼내서 없어질 애들 따로 담고 아닌 애들 queue에 다시 넣기
3. 외부환경 처리, 치즈랑 닿아있는 외부환경 처리
4. 치즈 위치 큐가 빌 때까지 진행

시간복잡도

1. O(n^2) ⇒ 10000
2. O(n^2) ⇒ 10000

그리고 치즈의 최대 부피는 98 * 98이라 50시간 이후 사라짐. 따라서 O(n^3)

코드(시간 초과)

```jsx
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
      this.start = new Node(args[0]);
      this.end = this.start;
      this.size += 1;
      for (let i = 1; i < args.length; i++) {
        let node = new Node(args[i]);
        this.end.connect(node);
        this.end = node;
        this.size += 1;
      }
    }
  }
  enqueue(value) {
    let node = new Node(value);
    if (this.start === null) {
      this.start = node;
      this.end = this.start;
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
console.log(board, isOuter, cheeze)
const delta = [[0, 1], [0, -1], [1, 0], [-1, 0]]

outerCheck(0, 0);
let cnt = 0;
const toRemove = []; // 없어질 치즈들
while (!cheeze.isEmpty()) {
  cnt += 1
  // 사라질 치즈 찾기
  let size = cheeze.getSize();
  for (let k = 0; k < size; k++) {
    let [i, j] = cheeze.dequeue();
    let outerSide = 0;
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

```

코드 수정 (큐를 좀 더 가볍게 구현하자) ⇒ 노드 클래스 대신 객체로 구현

```jsx
class Queue {
  constructor(...args) {
    this.store = {};
    this.front = 0;
    this.rear = this.front;
    if (args.length > 0) {
      for (let v of args) {
        this.enqueue(v);
      }
    }
  }
  enqueue(value) {
    if (this.rear === this.front) {
      this.store[this.front] = value;
      this.rear += 1;
      return;
    }
    this.store[this.rear] = value;
    this.rear += 1;
  }
  dequeue() {
    if (this.rear === this.front) { return null; }
    let ret = this.store[this.front];
    delete this.store[this.front];
    this.front += 1;
    return ret;
  }
  getSize() {
    return (this.rear - this.front);
  }
  isEmpty() {
    if (this.getSize() === 0) { return true; }
    return false;
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
    board[x][y] = 0; // 이전 코드에 이렇게 갱신 안했었음
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

```

코드 수정 ⇒ 첫번째 꺼 queue 크기 계산이 틀렸음 ⇒ 심지어 이게 더 빠름

```jsx
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

```