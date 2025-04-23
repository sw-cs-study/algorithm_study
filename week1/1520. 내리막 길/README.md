### 풀이

알고리즘

1. 출력 값이 10억이하의 음이 아닌 정수로 하나씩 세면 바로 시간초과다.
2. 바로 생각나는게 각 점을 도달하는 경우의 수를 모아서 한번에 이동시키는 것이다.
3. 그러려면 각 점에 도달하기 전까지 이전 경우의 수(높이가 높은 점들)를 “우선적으로” 처리해야 한다.
4. 따라서, 우선순위 큐를 사용해 각 지점의 높이를 기준으로 방문하며 경우의 수를 모아서 탐색한다.
5. 각 지점의 [높이, 좌표]를 우선순위 큐(최대힙)에 저장함
6. 경우의 수를 저장하는 이차원 배열도 선언

시간복잡도

모든 점을 한 번씩 방문하므로 O(n^2)

코드

```jsx
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

```