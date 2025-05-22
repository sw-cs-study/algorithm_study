/**
 * 컵라면
 *
 * 문제 잘못이해함.
 * 일단 데드라인 작은거, 데드라인이 같다면 컵라면 많이주는 거 순으로 정렬(우선순위큐1)
 * 하나씩 꺼내면서 다른 우선순위 큐(2)에 저장, 해당 우선순위 큐의 크기는 현재 시간임, 컵라면 크기가 작은 순으로 정렬
 * 1번을 돌면서 현재 시간과 데드라인이 같다면 2번에서 컵라면 크기가 가장 작은 문제랑 비교해서 바꿀지 말지 결정함
 *
 * 1번 n(log(n)) -> 3,600,000
 * 2번 n(log(n))
 * 시간복잡도: O(nlog(n))
 */

class Problem {
  constructor(deadLine, cups) {
    this.deadLine = deadLine;
    this.cups = cups;
  }
}

class PriorityQueue {
  constructor(compare = (a, b) => a - b) {
    this.queue = [];
    this.size = 0;
    this.compare = compare;
  }

  insert(v) {
    this.queue.push(v);
    this.size++;
    let cur = this.size - 1;
    let parent = Math.floor((cur - 1) / 2);
    while (cur > 0 && this.compare(this.queue[cur], this.queue[parent]) < 0) {
      [this.queue[cur], this.queue[parent]] = [
        this.queue[parent],
        this.queue[cur],
      ];
      cur = parent;
      parent = Math.floor((cur - 1) / 2);
    }
  }

  remove() {
    if (this.size === 1) {
      this.size--;
      this.first = null;
      return this.queue.pop();
    }

    let ret = this.queue[0];
    if (typeof this.queue[0] === Array) {
      ret = [...this.queue[0]];
    }
    this.queue[0] = this.queue.pop();
    this.size--;
    let cur = 0;
    let child = 2 * cur + 1;
    if (
      child + 1 < this.size &&
      this.compare(this.queue[child + 1], this.queue[child]) < 0
    ) {
      child++;
    }
    while (
      child < this.size &&
      this.compare(this.queue[child], this.queue[cur]) < 0
    ) {
      [this.queue[cur], this.queue[child]] = [
        this.queue[child],
        this.queue[cur],
      ];
      cur = child;
      child = 2 * child + 1;
      if (
        child + 1 < this.size &&
        this.compare(this.queue[child + 1], this.queue[child]) < 0
      ) {
        child++;
      }
    }
    return ret;
  }
  first() {
    if (this.size === 0) return null;
    return this.queue[0];
  }
}

const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const n = Number(input[0]);
// 데드라인 작은순, 보상 컵라면 큰 순
const problems = new PriorityQueue((a, b) => {
  if (a.deadLine === b.deadLine) return b.cups - a.cups;
  return a.deadLine - b.deadLine;
});
// 보상 컵라면 작은 순
const solved = new PriorityQueue((a, b) => a.cups - b.cups);
// 데드라인이랑 컵라면 저장
for (let i = 1; i < 1 + n; i++) {
  let [deadLine, cups] = input[i].split(" ").map(Number);
  problems.insert(new Problem(deadLine, cups));
}
// 데드라인 작은거, 데드라인 같다면 컵라면 큰거 순으로 뽑으면서 진행
while (problems.size > 0) {
  let cur = problems.remove();
  // 현재까지 담은 시간 보다 데드라인이 크면 그냥 추가
  if (cur.deadLine > solved.size) {
    solved.insert(cur);
  } else {
    // 같거나 작다면 현재까지 푼 문제 중에 컵라면 제일 작은거랑 비교
    if (cur.cups > solved.first().cups) {
      solved.remove();
      solved.insert(cur);
    }
  }
}
let answer = 0;
for (let cur of solved.queue) {
  answer += cur.cups;
}
console.log(answer);
