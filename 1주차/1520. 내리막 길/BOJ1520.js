const input = require('fs').readFileSync(process.platform === "linux" ? '/dev/stdin' : 'sample.txt').toString().trim().split('\n');

class MaxHeap {
  constructor(...args) {
    this.heap = [...args];
  }

  push(arr) {
    this.heap.push(arr);
    let idx = this.heap.length - 1;
    let parent = Math.floor((idx - 1) / 2);
    while (parent >= 0 && this.heap[parent][0] < this.heap[idx][0]) {
      [this.heap[parent], this.heap[idx]] = [this.heap[idx], this.heap[parent]]; // swap
      idx = parent;
      parent = Math.floor((idx - 1) / 2);
    }
  }

  pop() {
    if (this.heap.length === 1) { return this.heap.pop(); }
    let ret = [...this.heap[0]];
    this.heap[0] = this.heap.pop();
    let idx = 0;
    let child = 2 * idx + 1;
    if (child + 1 < this.heap.length && this.heap[child][0] < this.heap[child + 1][0]) {
      child += 1;
    }
    while (child < this.heap.length && this.heap[idx][0] < this.heap[child][0]) {
      [this.heap[idx], this.heap[child]] = [this.heap[child], this.heap[idx]]; // swap
      idx = child;
      child = 2 * idx + 1;
      if (child + 1 < this.heap.length && this.heap[child][0] < this.heap[child + 1][0]) {
        child += 1;
      }
    }
    return ret;
  }

  isEmpty() {
    if (this.heap.length === 0) { return true; }
    return false;
  }
}

const [n, m] = input[0].split(" ").map(Number);
const board = [];
for (let i = 0; i < n; i++) {
  board.push(input[1 + i].split(" ").map(Number));
}
const visited = [];
for (let i = 0; i < n; i++) {
  visited.push([]);
  for (let j = 0; j < m; j++) {
    visited[i].push(0);
  }
}
visited[0][0] = 1;

const delta = [[1, 0], [0, 1], [-1, 0], [0, -1]];

const pq = new MaxHeap([board[0][0], 0, 0]);
while (!pq.isEmpty()) {
  let [h, i, j] = pq.pop();
  for (let [di, dj] of delta) {
    let [ni, nj] = [i + di, j + dj];
    if (0 <= ni && ni < n && 0 <= nj && nj < m && board[ni][nj] < h) {
      if (visited[ni][nj] === 0) { // 첫 방문만 추가 
        pq.push([board[ni][nj], ni, nj])
      }
      visited[ni][nj] += visited[i][j];
    }
  }
}

console.log(visited[n - 1][m - 1])


