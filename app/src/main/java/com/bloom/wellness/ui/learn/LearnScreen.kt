package com.bloom.wellness.ui.learn

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bloom.wellness.ui.theme.BloomBorder
import com.bloom.wellness.ui.theme.BloomCreamBg
import com.bloom.wellness.ui.theme.BloomTextPrimary
import com.bloom.wellness.ui.theme.BloomTextSecondary

data class LearnArticle(
    val question: String,
    val answer: String
)

data class LearnSection(
    val category: String,
    val articles: List<LearnArticle>
)

/**
 * Exact Figma Learn Screen with categorized PCOS knowledge and accordion Q&A.
 */
@Composable
fun LearnScreen() {
    val sections = remember {
        listOf(
            LearnSection(
                category = "Diagnosis & basics",
                articles = listOf(
                    LearnArticle(
                        "What exactly is PCOS?",
                        "Polycystic ovary syndrome (PCOS) is a hormonal condition affecting people with ovaries. Key features include irregular periods, higher androgen (male hormones) levels, and ovaries that develop multiple small fluid-filled follicles."
                    ),
                    LearnArticle(
                        "What does PCOS stand for — and do I actually have cysts on my ovaries?",
                        "PCOS stands for Polycystic Ovary Syndrome. Despite the name, they aren't harmful cysts or tumors. They are simply immature egg follicles that halted development before ovulation."
                    ),
                    LearnArticle(
                        "What causes PCOS?",
                        "There is no single cause, but medical research points to genetics, insulin resistance, and low-grade systemic inflammation as primary drivers."
                    ),
                    LearnArticle(
                        "How is PCOS actually diagnosed?",
                        "Doctors typically use the Rotterdam criteria: meeting at least 2 out of 3 features: irregular cycles, clinical/biochemical signs of high androgens, and polycystic ovaries on ultrasound."
                    ),
                    LearnArticle(
                        "Is PCOS the same for everyone?",
                        "Not at all. PCOS exists across several phenotypes (insulin-resistant, inflammatory, adrenal, and post-pill), and symptoms vary widely between individuals."
                    )
                )
            ),
            LearnSection(
                category = "Symptoms & body",
                articles = listOf(
                    LearnArticle(
                        "Why are my periods irregular?",
                        "Elevated androgens and disrupted LH/FSH ratios often prevent regular monthly ovulation. Without ovulation, the uterine lining isn't cued to shed consistently."
                    ),
                    LearnArticle(
                        "Why does weight feel harder to manage?",
                        "Insulin resistance causes your body to pump out more insulin, a fat-storage hormone. This signals cells to store energy rather than burning it smoothly."
                    ),
                    LearnArticle(
                        "What is the deal with insulin resistance?",
                        "Around 70% of women with PCOS have insulin resistance. High insulin triggers ovaries to make more testosterone, worsening acne, hair issues, and irregular periods."
                    ),
                    LearnArticle(
                        "Is the acne and hair growth from PCOS treatable?",
                        "Yes. Balancing blood sugar, gentle movement, targeted supplements (like inositol and spearmint), and anti-androgenic therapies are proven to calm breakouts and unwanted hair."
                    )
                )
            ),
            LearnSection(
                category = "Managing it day to day",
                articles = listOf(
                    LearnArticle(
                        "Can food really make a difference?",
                        "Absolutely. Dietary choices directly impact insulin release. Eating balanced meals keeps your blood sugar flat, reducing ovarian androgen triggers."
                    ),
                    LearnArticle(
                        "What foods should I eat — and which should I limit?",
                        "Focus on high-fiber greens, quality proteins (eggs, poultry, tofu), healthy fats (olive oil, avocado), and complex grains. Reduce refined sugars and sugary beverages."
                    ),
                    LearnArticle(
                        "How can I manage insulin resistance day to day?",
                        "Walk for 10-15 minutes after meals, lift weights 2-3 times a week, drink ample water, and don't skip meals or fast excessively."
                    ),
                    LearnArticle(
                        "Will I need medication forever?",
                        "Not necessarily. Lifestyle habits form the foundation. Medications like Metformin or Spironolactone are tools that can be adapted as your body heals."
                    ),
                    LearnArticle(
                        "How does stress fit into all this?",
                        "Chronic high cortisol drains progesterone and provokes insulin resistance. Daily relaxation or breathwork helps calm adrenal testosterone production."
                    )
                )
            ),
            LearnSection(
                category = "Looking ahead",
                articles = listOf(
                    LearnArticle(
                        "Does PCOS affect fertility?",
                        "PCOS is the leading cause of ovulatory subfertility, but it does NOT mean infertility. Most women with PCOS conceive naturally or with minor ovulatory support once cycles stabilize."
                    ),
                    LearnArticle(
                        "What happens if PCOS is left unmanaged?",
                        "Long term, unmanaged insulin resistance increases risk for type 2 diabetes and cardiovascular disease. Consistent daily habits dramatically reduce these risks."
                    )
                )
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomCreamBg)
            .statusBarsPadding()
    ) {
        // Title Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Learn",
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = BloomTextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Understand PCOS, at your own pace",
                fontSize = 14.sp,
                color = BloomTextSecondary
            )
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            sections.forEach { section ->
                item {
                    Column {
                        Text(
                            text = section.category,
                            fontFamily = FontFamily.Serif,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = BloomTextPrimary,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            section.articles.forEach { article ->
                                LearnAccordionCard(article = article)
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun LearnAccordionCard(article: LearnArticle) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, BloomBorder)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = article.question,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = BloomTextPrimary,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowDown else Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = BloomTextSecondary,
                    modifier = Modifier.size(20.dp)
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = article.answer,
                        fontSize = 13.sp,
                        lineHeight = 19.sp,
                        color = BloomTextSecondary
                    )
                }
            }
        }
    }
}
