/**
 * 호숫가의 개미굴
 * 쪽방이 있다면 무조건 쪽방 우선?? -> 해당 쪽방이 달린 방은 못가게 표시 -> 그럼 구역이 분리가됨
 * -> 각 구역의 방에 대해서 이웃하지 않고 최대로 고르기 -> 각 구역의 크기 / 2이면 답임. 나머지가 있다면 +1
 *
 * 1. 각 구역을 돌면서 쪽방이 있는지 없는지 판단
 * 2. 쪽방이 없다면 구역 크기를 늘림
 * 3. 쪽방이 있다면 이전까지의 구역크기를 가지고 이웃하지 않고 고른 방의 최대 갯수 계산
 * 4. 반복문 나와서 현재 구역 크기가 있다면 다시 계산(-> 1번 구역에 연결되는지를 판단하고 계산)
 * 5. 1번과 N번이 연결되어 있기 때문에 1번부터 세기 보다는 처음 쪽방이 연결된 방부터 세는게 정확함
 * -> visited로 방문안한 방을 체크?(이건 굳이 메모리쓰는것 같음) -> 첫 번째와 마지막 section만 따로 체크? (이거)
 * 6. 방의 크기가 다 0이면 나머지가 있어도 +1을 하면 안됨
 *
 * 시간 + 공간
 * -> n <= 250,000 ->
 * -> Ci <= 10^12 = 2^40 -> BigInt로 처리해야함
 * 1.
 */
const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");
const n = Number(input[0]);
const state = input[1].split(" ").map(BigInt);
let answer = 0n;
let sectionSize = 0;
let firstSectionSize = 0;
let idx = 0;
// 1번을 포함한 구역
for (let i = 0; i < n; i++) {
  if (state[i] > 0n) break;
  firstSectionSize++;
  idx++;
}
// 나머지 구역
for (let i = idx; i < n; i++) {
  const v = state[i];
  if (v > 0n) {
    // 쪽방
    let count = Math.floor(sectionSize / 2);
    if (sectionSize % 2 > 0) count++;
    answer += v + BigInt(count);
    sectionSize = 0;
  } else {
    // 구역
    sectionSize++;
  }
}
firstSectionSize += sectionSize;
let count = Math.floor(firstSectionSize / 2);
if (firstSectionSize !== n && firstSectionSize % 2 > 0) count++;
answer += BigInt(count);

console.log(answer.toString());
