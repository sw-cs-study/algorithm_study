let players = [
  0, 2, 3, 3, 1, 2, 0, 0, 0, 0, 4, 2, 0, 6, 0, 4, 2, 13, 3, 5, 10, 0, 1, 5,
];
let m = 3;
let k = 5;

console.log(solution(players, m, k));

function solution(players, m, k) {
  var answer = 0;
  let n = players.length;

  let prefixSum = Array(n).fill(0);
  for (let i = 0; i < n; i++) {
    let online = players[i];

    if (i > 0) prefixSum[i] += prefixSum[i - 1];
    if (online < (prefixSum[i] + 1) * m) continue;

    let add = Math.floor((online - prefixSum[i] * m) / m);

    answer += add;
    prefixSum[i] += add;
    if (i + k < n) prefixSum[i + k] -= add;
  }
  return answer;
}
