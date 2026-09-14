function solution(scores) {
  let answer = 1;
  let [wa, wb] = scores[0];
  let wanho = wa + wb;

  // 근무 태도 점수 내림차순 + 동료 평가 점수가 같다면 동료 평가 점수 오름차순으로 정렬 (내림차순으로 정렬하면 아래에서 동료 평가 점수로 비교할때 보너스 못받는 애들을 못 솎아냄)
  scores.sort((a, b) => {
    if (a[0] !== b[0]) return b[0] - a[0];
    return a[1] - b[1];
  });
  // 완호 등수 구하기
  // 인센티브 못받는 애들은 치우면서, 인센티브 받는 애는 완호보다 합이 높은지 체크
  let maxB = 0;
  for (let i = 0; i < scores.length; i++) {
    let [a, b] = scores[i];
    // 인센티브 x
    if (b < maxB) {
      // 완호가 못받으면 리턴
      if (a === wa && b === wb) return -1;
      continue;
    }
    if (b > maxB) maxB = b;
    // 완호와 합 비교
    if (a + b > wanho) answer++;
  }

  return answer;
}
