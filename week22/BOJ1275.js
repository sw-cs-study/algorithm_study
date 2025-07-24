class Segment {
  constructor(arr) {
    this.arr = [...arr];
    this.leafStart = 1;
    while (arr.length > this.leafStart) this.leafStart <<= 1;
    this.tree = Array(this.leafStart * 2).fill(0n);
    this.init();
  }

  init() {
    // 리프노드 채우기
    for (let i = 0; i < this.arr.length; i++) {
      this.tree[this.leafStart + i] = BigInt(this.arr[i]);
    }

    // 리프노드에서 부모노드로 올라가며 구간처리하기
    for (let i = this.leafStart - 1; i > 0; i--) {
      this.tree[i] = this.tree[i * 2] + this.tree[i * 2 + 1];
    }
  }

  query(l, r) {
    l += this.leafStart - 1; // 입력되는 순서가 1-base, 트리 내부는 0-base
    r += this.leafStart - 1;
    let sum = 0n;
    while (l <= r) {
      if (l % 2 === 1) sum += BigInt(this.tree[l++]);
      if (r % 2 === 0) sum += BigInt(this.tree[r--]);
      l >>= 1;
      r >>= 1;
    }

    return sum;
  }

  update(idx, value) {
    idx--;
    this.arr[idx] = value;
    let cur = this.leafStart + idx;
    this.tree[cur] = BigInt(value);
    cur >>= 1;
    while (cur > 0) {
      this.tree[cur] = this.tree[cur * 2] + this.tree[cur * 2 + 1];
      cur >>= 1;
    }
  }
}

const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const [n, q] = input[0].split(" ").map(Number);
const seq = input[1].split(" ").map(Number);

const segmentTree = new Segment(seq);

let answer = "";
for (let i = 0; i < q; i++) {
  let [x, y, a, b] = input[2 + i].split(" ").map(Number);
  if (x > y) [x, y] = [y, x];
  answer += `${String(segmentTree.query(x, y))}\n`;
  segmentTree.update(a, b);
}

console.log(answer);
