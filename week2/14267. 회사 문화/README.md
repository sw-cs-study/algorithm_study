### 풀이1

알고리즘 

각 칭찬 횟수마다 칭찬 받은 정도를 갱신하면 터진다(n * m). 

1. 상사의 부하 직원을 나타내는 배열을 구한다.
2. 모든 칭찬에 대해 처음 칭찬 받은 사람에게 기록한다.
3. 상사부터 순회하며 직원의 칭찬정도를 높인다.

시간복잡도

1. O(n) ⇒ 100,000
2. O(m) ⇒ 100,000
3. O(n) ⇒ 100,000

따라서, O(n)

코드

```jsx
const input = require('fs').readFileSync(process.platform === "linux" ? '/dev/stdin' : 'sample.txt').toString().trim().split('\n');

let [n, m] = input[0].split(" ").map(Number);
const bossTable = [0, ...input[1].split(" ").map(Number)];
const answer = [];
const juniorTable = [];
for (let a of bossTable) {
  answer.push(0);
  juniorTable.push([]);
}
for (let i = 2; i < n + 1; i++) {
  juniorTable[bossTable[i]].push(i);
}

for (let i = 0; i < m; i++) {
  let [p, w] = input[2 + i].split(" ").map(Number);
  answer[p] += w;
}

for (let i = 2; i < n + 1; i++) {
  for (let p of juniorTable[i]) {
    answer[p] += answer[i];
  }
}

console.log(answer.slice(1).join(" "));

```