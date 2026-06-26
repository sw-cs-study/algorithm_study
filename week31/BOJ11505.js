const DIV = 1000000007n;

class Segment {
  constructor(arr) {
    this.arr = arr.map(BigInt);
    this.leafStart = 1;
    while (arr.length > this.leafStart) this.leafStart <<= 1;
    this.tree = Array(this.leafStart * 2).fill(1n);
    this.init();
  }

  init() {
    // 리프노드 채우기
    for (let i = 0; i < this.arr.length; i++) {
      this.tree[this.leafStart + i] = this.arr[i];
    }

    // 리프노드에서 부모노드로 올라가며 구간처리하기
    for (let i = this.leafStart - 1; i > 0; i--) {
      this.tree[i] = (this.tree[i * 2] * this.tree[i * 2 + 1]) % DIV;
    }
  }

  act(op, b, c) {
    if (op === 1) {
      // b번째 수를 c로 바꿈
      this.update(b, c);
    } else if (op === 2) {
      // b에서 c까지의 곱 구하기
      return this.query(b, c);
    }
  }

  query(l, r) {
    if (l > r) [l, r] = [r, l];
    l += this.leafStart - 1; // 입력되는 순서가 1-base, 트리 내부는 0-base
    r += this.leafStart - 1;
    if (l === r) return this.tree[l];
    let mul = 1n;
    while (l <= r) {
      if (l % 2 === 1) {
        mul *= this.tree[l++];
        mul %= DIV;
      }
      if (r % 2 === 0) {
        mul *= this.tree[r--];
        mul %= DIV;
      }
      l >>= 1;
      r >>= 1;
    }

    return mul;
  }

  update(idx, value) {
    idx--;
    this.arr[idx] = value;
    let cur = this.leafStart + idx;
    this.tree[cur] = BigInt(value);
    cur >>= 1;
    while (cur > 0) {
      this.tree[cur] = (this.tree[cur * 2] * this.tree[cur * 2 + 1]) % DIV;
      cur >>= 1;
    }
  }
}

const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const [n, m, k] = input[0].split(" ").map(Number);
let arr = [];
for (let i = 0; i < n; i++) {
  arr.push(Number(input[1 + i]));
}
const tree = new Segment(arr);
let answer = [];
for (let i = 0; i < m + k; i++) {
  let [a, b, c] = input[1 + n + i].split(" ").map(Number);
  let result = tree.act(a, b, c);
  if (a === 2) {
    answer.push(result);
  }
}
console.log(answer.join("\n"));
