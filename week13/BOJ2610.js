/**
 * 회의 준비
 * 서로 알고 있으면 같은 위원회 => 위원회가 여러 개임
 * 위원회의 수가 최대 => 빠지는 위원회 없음
 * 의사전달 거리의 최댓값이 최소가 되어야함 => 각 위원회당 최소 거리를 구해야함 => 다익스트라 + 모든 정점
 * => 플로이드 워셜 => O(n^3) => 10^6
 * 각 참석자들을 돌면서 같은 위원회에 있는 사람에 대해 의사전달 거리 구하기
 * => 각 참석자별로 다른 참석자에 대한 거리를 구하므로 O(n^2) => 10 ^ 4
 * 각 참석자들과 연결된 참석자들 골라서 배열에 넣고 방문처리. 배열에 넣은 애들끼리 의사전달 거리 구하기
 */

const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");
const n = Number(input[0]);
const m = Number(input[1]);
const dist = [...Array(n + 1)].map(() => Array(n + 1).fill(Infinity));
for (let i = 0; i < m; i++) {
  let [a, b] = input[2 + i].split(" ").map(Number);
  dist[a][b] = 1;
  dist[b][a] = 1;
}

for (let i = 1; i < n + 1; i++) {
  dist[i][i] = 0;
}

for (let k = 1; k < n + 1; k++) {
  for (let i = 1; i < n + 1; i++) {
    for (let j = 1; j < n + 1; j++) {
      if (dist[i][j] > dist[i][k] + dist[k][j]) {
        dist[i][j] = dist[i][k] + dist[k][j];
      }
    }
  }
}

const visited = Array(n + 1).fill(false);
let committeeNumber = 0;
let representatives = [];
for (let i = 1; i < n + 1; i++) {
  if (visited[i]) continue;
  committeeNumber++;
  const committee = [i];
  visited[i] = true;
  // i번째 참석자와 연결된 참석자들
  for (let j = i + 1; j < n + 1; j++) {
    if (dist[i][j] < Infinity) {
      committee.push(j);
      visited[j] = true;
    }
  }
  // 각 참석자들끼리 의사전달 거리 구하면서 대표자 선정
  let maxDist = Infinity;
  let representative = -1;
  for (const member of committee) {
    let curDist = 0;
    for (const otherMember of committee) {
      if (member === otherMember) continue;
      if (curDist < dist[member][otherMember]) {
        curDist = dist[member][otherMember];
      }
    }
    if (curDist < maxDist) {
      maxDist = curDist;
      representative = member;
    }
  }
  representatives.push(representative);
}
representatives.sort((a, b) => a - b);
let ans = `${committeeNumber}\n`;
ans += representatives.join("\n");
console.log(ans);
