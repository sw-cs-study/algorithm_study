/**
 * 펄스를 적용하고 구간의 합을 다 구해야함.
 * 500,000개로 이루어진 수열의 구간합을 다 구하려면 누적합을 사용하고 2개를 골라도 nC2로 터짐
 * 우리가 필요한 건 합이 최대인 구간이므로 누적합 배열에서 이런 구간의 후보군을 찾는 방법을 생각해야함.
 * 누적합 배열은 특정 i를 끝으로 하는 연속 부분 수열의 합 중에서 가장 큰 값을 구할 수 있음.
 * i보다 앞에 존재하는 누적합 값 중에 최솟값을 i번째 누적합 값에서 빼면 최댓값임.
 * 이를 이용해 각 원소를 끝으로 하는 연속 부분 수열의 합 중에 가장 큰 값을 구해서 비교하면 답을 구할 수 있음.
 * O(N)
 */

function solution(sequence) {
  var answer = -Infinity;
  let n = sequence.length;
  let prefixSum1 = [...sequence];
  let prefixSum2 = [...sequence];

  for (let i = 0; i < n; i++) {
    let pulse1 = Math.pow(-1, i % 2);
    let pulse2 = Math.pow(-1, (i + 1) % 2);

    prefixSum1[i] *= pulse1;
    prefixSum2[i] *= pulse2;
  }

  let min = 0;
  for (let i = 0; i < n; i++) {
    if (i > 0) prefixSum1[i] += prefixSum1[i - 1];

    let sum = prefixSum1[i];
    if (min !== Infinity) sum -= min;

    if (sum > answer) answer = sum;

    if (prefixSum1[i] < min) min = prefixSum1[i];

    if (answer === null) console.log(i, prefixSum1[i], sum, min);
  }

  min = 0;
  for (let i = 0; i < n; i++) {
    if (i > 0) prefixSum2[i] += prefixSum2[i - 1];

    let sum = prefixSum2[i];
    if (min !== Infinity) sum -= min;

    if (sum > answer) answer = sum;

    if (prefixSum2[i] < min) min = prefixSum2[i];

    if (answer === null) console.log(i, prefixSum2[i], sum, min);
  }

  return answer;
}

console.log(solution([3, -6, 1]));
